package org.vaadin.covid.dialogs;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DeleteConfirmationDialog extends Dialog {

    public static Button abort = new Button("Abbrechen");
    public static Button delete = new Button("Löschen");


    public DeleteConfirmationDialog(String deleteElement) {

        configureComponents();
        add(createDialogLayout(deleteElement));

        setWidth(50f, Unit.PERCENTAGE);
        setHeight(40f, Unit.PERCENTAGE);
        setCloseOnOutsideClick(false);
    }

    private void configureComponents() {
        delete.setIcon(VaadinIcon.TRASH.create());
        delete.addThemeName("primary error");

        abort.setIcon(VaadinIcon.CLOSE.create());
        abort.addThemeName("error");
        abort.addClickListener(e -> {
            close();
        });

    }



    private static FormLayout createDialogLayout(String deleteElement) {
        FormLayout dialogLayout = new FormLayout();
        dialogLayout.add(new H1("Möchtest du " + deleteElement + " wirklich löschen?"));
        dialogLayout.add(new Label("Bitte bestätigen Sie die Löschung. Diese Aktion kann nicht rückgängig " +
                "gemacht werden."));
        dialogLayout.add(delete, abort);

        return dialogLayout;
    }

    public void deleteAddClickListener(ComponentEventListener listener) {
        delete.addClickListener(listener);
    }
}
