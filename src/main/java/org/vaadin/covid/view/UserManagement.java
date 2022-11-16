package org.vaadin.covid.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.covid.dialogs.NewUserDialog;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.layouts.MainLayout;
import org.vaadin.covid.manager.UserManager;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@PageTitle("CovidTracker")
@Route(value = "userManagement", layout = MainLayout.class)
public class UserManagement extends VerticalLayout {

    private final UserManager userManager;

    public UserManagement(UserManager userManager) {
        this.userManager = userManager;

        setSizeFull();

        initComponents();

        add();
    }

    private void initComponents() {
        HorizontalLayout horizontalLayoutAddButton = new HorizontalLayout();
        Button buttonAddUser = new Button("Neuer Benutzer");
        buttonAddUser.setIcon(new Icon(VaadinIcon.PLUS));
        buttonAddUser.addClickListener(buttonClickEvent -> {openUserDialog();});
        horizontalLayoutAddButton.add(buttonAddUser);
        add(horizontalLayoutAddButton);

        Grid<Users> grid = new Grid<>();
        grid.setAllRowsVisible(true);
        grid.setNestedNullBehavior(Grid.NestedNullBehavior.ALLOW_NULLS);
        grid.setColumnReorderingAllowed(true);
        grid.setItems(userManager.findAll());
        grid.setHeight(100f, Unit.PERCENTAGE);
        grid.addColumn(Users::getUsername).setHeader("Nutzer").setAutoWidth(true);
        grid.addColumn(Users::getRole).setHeader("Rolle").setAutoWidth(true);

        grid.addComponentColumn(users -> createPermissionIcon(users.getEnabled()))
                .setHeader("Freigeschaltet");

        grid.addColumn(Users::getCreationDate).setHeader("Erstellt am").setAutoWidth(true);
        grid.addColumn(Users::getLastLogin).setHeader("Zuletzt gesehen").setAutoWidth(true);
        add(grid);
    }

    private void openUserDialog() {
        NewUserDialog newUserDialog = new NewUserDialog(userManager);
        newUserDialog.open();
    }

    private Icon createPermissionIcon(boolean hasPermission) {
        Icon icon;
        if (hasPermission) {
            icon = createIcon(VaadinIcon.CHECK, "Yes");
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = createIcon(VaadinIcon.CLOSE_SMALL, "No");
            icon.getElement().getThemeList().add("badge error");
        }
        return icon;
    }

    private Icon createIcon(VaadinIcon vaadinIcon, String label) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        return icon;
    }
}
