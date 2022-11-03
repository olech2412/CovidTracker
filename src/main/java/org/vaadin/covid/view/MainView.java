package org.vaadin.covid.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.*;
import lombok.extern.log4j.Log4j2;
import org.vaadin.covid.ownComponents.Divider;
import org.vaadin.covid.repository.StatusRepository;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.Optional;

@PermitAll
@Log4j2
public class MainView extends AppLayout implements BeforeEnterObserver  {
    StatusRepository statusRepository;
    private final Tabs menu;
    private H1 viewTitle;

    public MainView(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;

        // Use the drawer for the menu
        setPrimarySection(Section.DRAWER);

        // Make the nav bar a header
        addToNavbar(true, createHeaderContent());

        // Put the menu in the drawer
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
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
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab("Home", HomeView.class),
                createTab("Bundesrepublik Deutschland", BrdView.class),
        };
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
