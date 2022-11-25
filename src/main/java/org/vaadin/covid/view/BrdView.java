package org.vaadin.covid.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.covid.layouts.MainLayout;

import javax.annotation.security.PermitAll;

@PageTitle("CovidTracker")
@Route(value = "bundesrepublikDeutschland", layout = MainLayout.class)
@PermitAll
public class BrdView extends VerticalLayout {
    public BrdView() {
        setSizeFull();
        add(new H1("Hier stehen in Zukunft die Daten für die Bundesrepublik Deutschland :)"));
    }
}