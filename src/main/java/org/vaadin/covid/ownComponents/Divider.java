package org.vaadin.covid.ownComponents;

import com.vaadin.flow.component.html.Span;

public class Divider extends Span {

    public Divider() {
        getStyle().set("background-color", "rgb(35,51,72)");
        getStyle().set("flex", "0 0 1.2px");
        getStyle().set("align-self", "stretch");
    }
}
