package org.vaadin.covid.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Daten Bundesrepublik Deutschland")
@Route(value = "bundesrepublikDeutschland", layout = MainView.class)
public class BrdView extends VerticalLayout {
    public BrdView() {
        setSizeFull();
        add();
    }
}