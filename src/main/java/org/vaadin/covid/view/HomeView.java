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
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.covid.jpa.Brd;
import org.vaadin.covid.repository.BrdRepository;
import org.vaadin.covid.repository.StatusRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@PageTitle("Home")
@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {
    StatusRepository statusRepository;
    BrdRepository brdRepository;
    public HomeView(BrdRepository brdRepository, StatusRepository statusRepository) {
        this.brdRepository = brdRepository;
        this.statusRepository = statusRepository;

        setSizeFull();
        add(new H1("Willkommen beim CovidTracker"), new Text("Über das Menü auf der linken Seite können Sie unter dem Reiter \"Daten abrufen\" die gewünschten Daten abfragen"), new Anchor("https://experience.arcgis.com/experience/478220a4c454480e823b17327b2bf1d4", "Datenquelle"));
        buildInzidenzChart();

    }

    private void buildInzidenzChart() {
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
                .withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
                .withSeries(new Series<>("7-Tage-Inzidenzwert:", brdRepository.findAll().stream().map(Brd::getIncidence7days).toArray()))
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Inzidenz ab: " + statusRepository.findFirstByCreationDateBefore(LocalDate.now()).getCreationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        .withAlign(Align.left).build())
                .withSubtitle(TitleSubtitleBuilder.get()
                        .withText("Verlauf der 7-Tage-Inzidenz")
                        .withAlign(Align.left).build())
                .withLabels(brdRepository.findAll().stream().map(Brd::getCreationDate).map(LocalDate::toString).toArray(String[]::new))
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
