package org.vaadin.covid.view;

import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.Annotations;
import com.github.appreciated.apexcharts.config.Markers;
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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.covid.jpa.Brd;
import org.vaadin.covid.repository.BrdRepository;
import org.vaadin.covid.repository.StatusRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@PageTitle("Home")
@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {
    StatusRepository statusRepository;
    BrdRepository brdRepository;
    Button refresh;
    SimpleTimer simpleTimer;
    SimpleTimer simpleTimer2;
    public HomeView(BrdRepository brdRepository, StatusRepository statusRepository) {
        this.brdRepository = brdRepository;
        this.statusRepository = statusRepository;

        simpleTimer = new SimpleTimer(new BigDecimal("10"));
        simpleTimer2 = new SimpleTimer(new BigDecimal("5"));

        simpleTimer2.setVisible(true);
        simpleTimer2.setMinutes(true);
        simpleTimer2.setFractions(true);
        simpleTimer.setVisible(true);
        simpleTimer.setMinutes(true);
        simpleTimer.setFractions(true);

        refresh = new Button("Refresh");
        refresh.addClickListener(e -> {
            refreshIt();
        });

        simpleTimer.addTimerEndEvent(e -> {
            doStuff();
        });
        simpleTimer2.addTimerEndEvent(e -> {
            System.out.println("Timer 2 ended");
            simpleTimer2.reset();
        });
        setSizeFull();
        add(new H1("Willkommen beim CovidTracker"), new Text("Über das Menü auf der linken Seite können Sie unter dem Reiter \"Daten abrufen\" die gewünschten Daten abfragen"), new Anchor("https://experience.arcgis.com/experience/478220a4c454480e823b17327b2bf1d4", "Datenquelle"), simpleTimer, simpleTimer2, refresh);
        simpleTimer.start();
        simpleTimer2.start();
        buildInzidenzChart();

    }

    private void refreshIt() {
        simpleTimer.reset();
        simpleTimer2.reset();
    }

    private void doStuff() {
        simpleTimer.pause();
        System.out.println(simpleTimer.getCurrentTime());
        VaadinSession.getCurrent().getSession().invalidate();
    }

    private void buildInzidenzChart() {
        List<Brd> data = brdRepository.findAll();
        data.sort(Comparator.comparing(Brd::getCreationDate));
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
                .withSeries(new Series<>("7-Tage-Inzidenzwert:", data.stream().map(Brd::getIncidence7days).toArray()))
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
