package org.vaadin.covid.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Covid-Data")
@Route(value = "data", layout = MainView.class)
public class DataView extends VerticalLayout {
    public DataView() {
        setSizeFull();
        add();
    }
}