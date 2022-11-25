package org.vaadin.covid.dialogsUtils;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ConfirmDialog extends Dialog {

    public Button abort = new Button("Abbrechen");
    public Button submit = new Button("Bestätigen");

    public H1 confirmationQuestionH1 = new H1("Möchtest du die Aktion wirklich ausführen?");


    public ConfirmDialog() {

        configureComponents();
        createDialogLayout(submit, abort, confirmationQuestionH1);

        setWidth(50f, Unit.PERCENTAGE);
        setHeight(40f, Unit.PERCENTAGE);
        setCloseOnOutsideClick(false);
    }

    private void configureComponents() {
        submit.setIcon(VaadinIcon.CHECK.create());
        submit.addThemeName("primary success");
        submit.addClickListener(e->{
            close();
        });

        abort.setIcon(VaadinIcon.CLOSE.create());
        abort.addThemeName("secondary error");
        abort.addClickListener(e -> {
            close();
        });

    }



    private void createDialogLayout(Button submit, Button abort, H1 confirmationQuestionH1) {
        add(confirmationQuestionH1);
        HorizontalLayout controlButtons = new HorizontalLayout(submit, abort);
        controlButtons.setWidth(100f, Unit.PERCENTAGE);
        abort.getElement().getStyle().set("margin-left", "auto");
        add(controlButtons);
    }

    public void deleteAddClickListener(ComponentEventListener listener) {
        submit.addClickListener(listener);
    }

    public void setConfirmationQuestion(String confirmationQuestion){
        confirmationQuestionH1.setText(confirmationQuestion);
    }
}
