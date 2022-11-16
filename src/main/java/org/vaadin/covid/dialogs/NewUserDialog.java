package org.vaadin.covid.dialogs;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.manager.UserManager;


public class NewUserDialog extends Dialog {

    public static TextField userName = new TextField("Nutzername");
    public static Select<String> role = new Select<>("Rolle");
    public static EmailField email = new EmailField("E-Mail");
    public static Button save = new Button("Nutzer erstellen");
    public static Button cancel = new Button("Abbrechen");
    private final UserManager userManager;


    public NewUserDialog(UserManager userManager) {
        this.userManager = userManager;

        configureComponents();
        add(createDialogLayout());

        save.addClickListener(e -> {
            saveNewUser();
        });

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

        userName.setRequired(true);
        userName.setRequiredIndicatorVisible(true);
        userName.setErrorMessage("Bitte geben Sie einen Nutzernamen ein");
        userName.setMaxLength(50);

        email.setRequiredIndicatorVisible(true);
        email.setErrorMessage("Bitte geben Sie eine gültige E-Mail Adresse ein.");
        email.setMaxLength(100);
    }

    private void saveNewUser() {
        Users user = new Users();
        user.setUsername(userName.getValue());
        user.setRole(role.getValue());
        user.setEmail(email.getValue());
        userManager.saveUser(user);
    }

    private static FormLayout createDialogLayout() {
        FormLayout dialogLayout = new FormLayout();
        dialogLayout.add(new H1("Neuer Benutzer"));
        dialogLayout.add(new Label("Bitte geben Sie die Daten des neuen Benutzers ein. Das Passwort wird " +
                "automatisch generiert und an die angegebene E-Mail-Adresse gesendet."));
        dialogLayout.add(userName, role, email);
        dialogLayout.add(new HorizontalLayout(save, cancel));

        return dialogLayout;
    }
}
