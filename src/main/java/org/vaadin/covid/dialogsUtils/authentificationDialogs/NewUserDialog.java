package org.vaadin.covid.dialogsUtils.authentificationDialogs;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.manager.UserManager;


public class NewUserDialog extends Dialog {

    public TextField userName = new TextField("Nutzername");
    public Select<String> role = new Select<>("Rolle");
    public EmailField email = new EmailField("E-Mail");
    public Button save = new Button("Nutzer erstellen");
    public Button cancel = new Button("Abbrechen");
    private final UserManager userManager;


    public NewUserDialog(UserManager userManager, Grid<Users> grid) {
        this.userManager = userManager;

        configureComponents();
        add(createDialogLayout(userName, email, role, save, cancel));

        save.addClickListener(e -> {
            saveNewUser();
            close();
        });
        save.setEnabled(false);

        cancel.addClickListener(e -> {
            close();
        });

        setWidth(50f, Unit.PERCENTAGE);
        setHeight(50f, Unit.PERCENTAGE);
        setCloseOnOutsideClick(false);

    }

    private void configureComponents() {
        save.setIcon(VaadinIcon.CHECK.create());
        save.addThemeName("primary success");
        cancel.setIcon(VaadinIcon.CLOSE.create());
        cancel.addThemeName("error");

        role.setItems("ROLE_USER", "ROLE_ADMIN");
        role.setValue("ROLE_USER");
        role.setErrorMessage("Bitte wählen Sie eine Rolle aus");
        role.setRequiredIndicatorVisible(true);
        role.addValueChangeListener(e -> {
            save.setEnabled(!userName.getValue().isEmpty() && !email.getValue().isEmpty() && !role.getValue().isEmpty());
        });


        userName.setRequired(true);
        userName.setRequiredIndicatorVisible(true);
        userName.setErrorMessage("Bitte geben Sie einen Nutzernamen ein");
        userName.setMaxLength(50);
        userName.addInputListener(e -> {
            save.setEnabled(!userName.getValue().isEmpty() && !email.getValue().isEmpty() && !role.getValue().isEmpty());
        });
        userName.addValueChangeListener(e -> {
            save.setEnabled(!userName.getValue().isEmpty() && !email.getValue().isEmpty() && !role.getValue().isEmpty());
        });

        email.setRequiredIndicatorVisible(true);
        email.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse ein.");
        email.setMaxLength(100);
        email.addInputListener(e -> {
            save.setEnabled(!userName.getValue().isEmpty() && !email.getValue().isEmpty() && !role.getValue().isEmpty());
        });
        email.addValueChangeListener(e -> {
            save.setEnabled(!userName.getValue().isEmpty() && !email.getValue().isEmpty() && !role.getValue().isEmpty());
        });
    }

    private void saveNewUser() {
        if(role.isEmpty() || userName.isEmpty() || email.isEmpty()) {
            Notification notification = new Notification("Bitte füllen Sie alle Felder aus.", 3000, Notification.Position.BOTTOM_START);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }else {
            Users user = new Users();
            user.setUsername(userName.getValue());
            user.setRole(role.getValue());
            user.setEmail(email.getValue());
            user.setLastIp(UI.getCurrent().getSession().getBrowser().getAddress());
            userManager.createNewUser(user);
        }
    }

    private static FormLayout createDialogLayout(TextField userName, EmailField email, Select<String> role, Button save, Button cancel) {
        FormLayout dialogLayout = new FormLayout();
        dialogLayout.add(new H1("Neuer Benutzer"));
        dialogLayout.add(new Label("Bitte geben Sie die Daten des neuen Benutzers ein. Das Passwort wird " +
                "automatisch generiert und an die angegebene E-Mail-Adresse gesendet."));
        dialogLayout.add(userName, role, email);
        dialogLayout.add(new HorizontalLayout(save, cancel));

        return dialogLayout;
    }

    public void configureSaveClickListener(ComponentEventListener listener) {
        save.addClickListener(listener);
    }
}
