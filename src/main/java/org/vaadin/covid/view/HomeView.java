package org.vaadin.covid.view;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.covid.jpa.Brd;
import org.vaadin.covid.layouts.MainLayout;
import org.vaadin.covid.repository.BrdRepository;
import org.vaadin.covid.repository.StatusRepository;
import org.vaadin.covid.staticResources.StaticTextMainView;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@PageTitle("CovidTracker")
@PermitAll
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    StatusRepository statusRepository;
    BrdRepository brdRepository;
    public HomeView(BrdRepository brdRepository, StatusRepository statusRepository) {
        this.brdRepository = brdRepository;
        this.statusRepository = statusRepository;

        setSizeFull();
        configureText();
        add(new Anchor("https://experience.arcgis.com/experience/478220a4c454480e823b17327b2bf1d4", "Datenquelle"));
        buildInzidenzChart();

    }

    private void configureText() {
        H1 h1 = new H1(StaticTextMainView.MAIN_VIEW_HEADER);
        h1.setId("main-view-header");
        Label mainViewDescription = new Label(StaticTextMainView.MAIN_VIEW_DESCRIPTION);
        mainViewDescription.setId("main-view-description");

        add(h1, mainViewDescription);
    }

    private void buildInzidenzChart() {
        List<Brd> data = brdRepository.findAll();
        data.sort(Comparator.comparing(Brd::getCreationDate));
        List<Double> incidences = new ArrayList<>();
        for (Brd brd : data) {
            incidences.add(brd.getIncidence7days());
        }
        ApexCharts areaChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.area)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(true)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.smooth).build())
                .withSeries(new Series<>("7-Tage-Inzidenzwert:", incidences.toArray()))
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Inzidenz ab: " + statusRepository.findFirstByCreationDateBefore(LocalDate.now()).getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .withAlign(Align.left).build())
                .withSubtitle(TitleSubtitleBuilder.get()
                        .withText("Verlauf der 7-Tage-Inzidenz")
                        .withAlign(Align.left).build())
                .withLabels(data.stream().map(Brd::getCreationDate).map(LocalDate::toString).toArray(String[]::new))
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.datetime).build())
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(true).build())
                .withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build())
                .build();
        areaChart.setHeight(500, Unit.PIXELS);
        add(areaChart);
    }
}
