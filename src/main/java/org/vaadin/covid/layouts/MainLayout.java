package org.vaadin.covid.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.covid.jpa.authentification.Users;
import org.vaadin.covid.manager.UserManager;
import org.vaadin.covid.ownComponents.Divider;
import org.vaadin.covid.repository.StatusRepository;
import org.vaadin.covid.security.SecurityService;
import org.vaadin.covid.view.BrdView;
import org.vaadin.covid.view.HomeView;
import org.vaadin.covid.view.SettingsView;
import org.vaadin.covid.view.UserManagement;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PermitAll
@Log4j2
public class MainLayout extends AppLayout implements BeforeEnterObserver  {
    StatusRepository statusRepository;
    private final Tabs menu;
    private H1 viewTitle;
    @Autowired
    SecurityService securityService;

    private final UserManager userManager;

    public MainLayout(StatusRepository statusRepository, SecurityService securityService, UserManager userManager) {
        this.statusRepository = statusRepository;
        this.securityService = securityService;
        this.userManager = userManager;

        // Use the drawer for the menu
        setPrimarySection(Section.DRAWER);

        // Make the nav bar a header
        addToNavbar(true, createHeaderContent());

        // Put the menu in the drawer
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));

        checkIPAndSetLastLogin();

        SystemMessagesProvider systemMessagesProvider = new SystemMessagesProvider() {
            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
                CustomizedSystemMessages messages = new CustomizedSystemMessages();
                messages.setSessionExpiredNotificationEnabled(false);
                messages.setSessionExpiredURL("https://localhost:8443/login");
                return messages;
            }
        };

        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(30*60);
        UI.getCurrent().getSession().getSession().setMaxInactiveInterval(30*60);
        VaadinService.getCurrent().setSystemMessagesProvider(systemMessagesProvider);
    }

    private void checkIPAndSetLastLogin() {
        Users user = userManager.getUserByUsername(securityService.getAuthenticatedUser().getUsername());
        if(!user.getLastIp().equals(VaadinSession.getCurrent().getBrowser().getAddress())){
            user.setLastIp(VaadinSession.getCurrent().getBrowser().getAddress());
            log.info("IP changed for user: " + user.getUsername());
        }else {
            log.info("IP not changed for user: " + securityService.getAuthenticatedUser().getUsername());
        }
        user.setLastLogin(LocalDateTime.now());
        userManager.saveUser(user);

    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout lastUpdate = new HorizontalLayout();

        //Configure layout wehere last Update will be displayed
        lastUpdate.setId("lastUpdate");
        lastUpdate.add(new Span("Update: " + getLastUpdate().toString()));
        lastUpdate.getStyle().set("margin-left", "auto");

        // Configure styling for the header
        layout.setId("header");
        //layout.getThemeList().set("dark", true);
        layout.setWidth(100f, Unit.PERCENTAGE);
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Have the drawer toggle button on the left
        layout.add(new DrawerToggle());

        // Placeholder for the title of the current view.
        // The title will be set after navigation.
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(lastUpdate);

        return layout;
    }

    private String getLastUpdate() {

        return statusRepository.findAllByCreationDate(LocalDate.now()).get(0).getLastUpdate();
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();

        // Configure styling for the drawer
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        StreamResource logoStream = new StreamResource("icon.png", () -> getClass().getResourceAsStream("/static/img/icon.png"));
        Image logoImage = new Image(logoStream, "Logo");
        logoImage.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));
        logoImage.setTitle("Logo christopho");
        logoImage.setSizeFull();
        logoLayout.add(logoImage);

        // Display the logo and the menu in the drawer
        layout.add(logoLayout,new Divider(), menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED);
        tabs.setId("tabs");
        tabs.add(createMenuItems());

        Button logout = new Button("Logout");
        logout.setIcon(new Icon(VaadinIcon.SIGN_OUT));
        logout.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL);
        logout.addClickListener(e -> {
            securityService.logout();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        tabs.add(logout);
        return tabs;
    }

    private Component[] createMenuItems() {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Home", HomeView.class));
        tabs.add(createTab("Bundesrepublik Deutschland", BrdView.class));
        tabs.add(createTab("Einstellungen", SettingsView.class));
        if(securityService.getAuthenticatedUser().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            tabs.add(createTab("User Management", UserManagement.class));
        }
        return tabs.toArray(new Tab[0]);

    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        log.info("User: " + VaadinSession.getCurrent().getSession().getId() + " entered MainView -- " + "Device information: " + VaadinSession.getCurrent().getBrowser().getBrowserApplication());
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Select the tab corresponding to currently shown view
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Set the view title in the header
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
