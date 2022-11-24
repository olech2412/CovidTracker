package org.vaadin.covid.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.vaadin.covid.dialogsUtils.DeleteConfirmationDialog;
import org.vaadin.covid.dialogsUtils.authentificationDialogs.EditUserDialog;
import org.vaadin.covid.dialogsUtils.authentificationDialogs.NewUserDialog;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.layouts.MainLayout;
import org.vaadin.covid.manager.UserManager;
import org.vaadin.covid.ownComponents.GridOperations;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@PageTitle("CovidTracker")
@Route(value = "userManagement", layout = MainLayout.class)
@Log4j2
public class UserManagement extends VerticalLayout implements GridOperations {

    private final UserManager userManager;
    private final Grid<Users> grid = new Grid<>();
    Users selectedUser = null;

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
        buttonAddUser.addClickListener(buttonClickEvent -> {
            openNewUserDialog();});
        horizontalLayoutAddButton.add(buttonAddUser);
        add(horizontalLayoutAddButton);

        configureGrid(grid);
        prepareContextMenu(grid);


        add(grid);
    }

    private void prepareContextMenu(Grid<Users> grid) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(grid);
        contextMenu.addItem("Benutzer löschen", e -> {
            if (selectedUser != null) {
                openUserDeleteDialog(selectedUser);
                grid.setItems(userManager.findAll());
            }else{
                log.debug("No user selected");
            }
        });
        contextMenu.addItem("Benutzer bearbeiten", e -> {
            if (selectedUser != null) {
                openUserEditDialog(selectedUser);
            }else{
                log.debug("No user selected");
            }
        });
        contextMenu.addItem("Benutzer aktivieren/deaktivieren", e -> {
            if (selectedUser != null) {
                Users user = selectedUser;
                user.setEnabled(!selectedUser.getEnabled());
                userManager.updateUser(user);
                update(user);
            }else{
                log.debug("No user selected");
            }
        });
    }

    private void openUserEditDialog(Users selectedUser) {
        EditUserDialog editUserDialog = new EditUserDialog(userManager, selectedUser, grid);
        editUserDialog.addDialogCloseActionListener(e -> {
            update(selectedUser);
        });
        editUserDialog.open();
    }

    private void openUserDeleteDialog(Users selectedUser) {
        DeleteConfirmationDialog newUserDeleteDialog = new DeleteConfirmationDialog(selectedUser.getUsername());
        newUserDeleteDialog.deleteAddClickListener(buttonClickEvent -> {
            userManager.deleteUser(selectedUser);
            delete(selectedUser);
            newUserDeleteDialog.close();
        });
        newUserDeleteDialog.open();

    }

    private void configureGrid(Grid<Users> grid) {
        grid.setAllRowsVisible(true);
        grid.setNestedNullBehavior(Grid.NestedNullBehavior.ALLOW_NULLS);
        grid.setColumnReorderingAllowed(true);
        grid.setItems(userManager.findAll());
        grid.setHeight(100f, Unit.PERCENTAGE);
        grid.addColumn(Users::getUsername).setHeader("Nutzer").setAutoWidth(true);
        grid.addColumn(Users::getRole).setHeader("Rolle").setAutoWidth(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addComponentColumn(users -> createPermissionIcon(users.getEnabled()))
                .setHeader("Freigeschaltet");

        grid.addColumn(Users::getCreationDate).setHeader("Erstellt am").setAutoWidth(true);
        grid.addColumn(Users::getLastLogin).setHeader("Zuletzt gesehen").setAutoWidth(true);

        grid.addCellFocusListener(e -> {
            if(e.getItem().isPresent()){
                selectedUser = e.getItem().get();
            }else {
                log.debug("No user selected");
            }
        });
    }

    private void openNewUserDialog() {
        NewUserDialog newUserDialog = new NewUserDialog(userManager, grid);
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

    /**
     * Updates the grid with the new data
     */
    @Override
    public void save(Object saveElement) {

    }

    /**
     * Can be used to update the grid after a delete
     */
    @Override
    public void delete(Object deleteElement) {
        grid.getDataProvider().refreshItem((Users) deleteElement);
    }

    /**
     * Updates the grid after a change in the database
     */
    @Override
    public void update(Object updateElement) {
        grid.getDataProvider().refreshItem((Users) updateElement);
    }
}
