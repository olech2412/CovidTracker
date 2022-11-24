package org.vaadin.covid.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.layouts.MainLayout;
import org.vaadin.covid.manager.UserManager;
import org.vaadin.covid.security.SecurityService;
import org.vaadin.covid.staticResources.StaticTextSettingsView;

import javax.annotation.security.PermitAll;

@PageTitle("CovidTracker-Settings")
@Route(value = "settings", layout = MainLayout.class)
@PermitAll
public class SettingsView extends VerticalLayout {

    private final UserManager userManager;
    @Autowired
    SecurityService securityService;

    private TextField usernameField = new TextField();
    private TextField email = new TextField();
    private TextField creationDate = new TextField();
    private TextField lastLoginDate = new TextField();
    private TextField lastIp = new TextField();
    private PasswordField password = new PasswordField();
    private Button save = new Button("Speichern");


    public SettingsView(UserManager userManager, SecurityService securityService) {
        this.securityService = securityService;
        this.userManager = userManager;

        setSizeFull();
        configureText();
        configureContent(securityService.getAuthenticatedUser().getUsername());
        configureLayout();

    }

    private void configureContent(String username) {
        usernameField.setEnabled(false);
        email.setEnabled(false);
        creationDate.setEnabled(false);
        lastLoginDate.setEnabled(false);
        lastIp.setEnabled(false);

        save.setEnabled(false);

        usernameField.setTitle("Username");
        email.setTitle("Email");
        creationDate.setTitle("Erzeugungsdatum");
        lastLoginDate.setTitle("Letzter Login");
        lastIp.setTitle("Letzte IP");
        password.setTitle("Passwort");
        password.addValueChangeListener(e -> {
            save.setEnabled(e.getValue().length() > 0);
        });

        save.setIcon(VaadinIcon.CHECK.create());
        save.addThemeName("primary success");
        save.addClickListener(e -> {
            Users user = userManager.getUserByUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password.getValue()));
            userManager.saveUser(user);
            password.clear();
            save.setEnabled(false);
            Notification notification = new Notification("Passwort wurde geändert", 3000, Notification.Position.BOTTOM_START);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
        });

        Users user = userManager.getUserByUsername(username);
        usernameField.setValue(username);
        email.setValue(user.getEmail());
        creationDate.setValue(user.getCreationDate().toString());
        lastLoginDate.setValue(user.getLastLogin().toString());
        lastIp.setValue(user.getLastIp());
    }

    private void configureLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.add(usernameField, email);
        formLayout.add(creationDate, lastLoginDate, lastIp);
        formLayout.add(password, save);

        add(formLayout);
    }

    private void configureText() {
        H1 h1 = new H1(StaticTextSettingsView.SETTINGS_VIEW_HEADER);
        h1.setId("main-view-header");
        Label mainViewDescription = new Label(StaticTextSettingsView.SETTINGS_VIEW_DESCRIPTION);
        mainViewDescription.setId("main-view-description");

        add(h1, mainViewDescription);
    }
}
