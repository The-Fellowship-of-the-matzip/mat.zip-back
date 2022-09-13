package com.woowacourse.matzip.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.woowacourse.matzip.ui.member.MemberListView;
import com.woowacourse.matzip.ui.restaurant.RestaurantListView;
import com.woowacourse.matzip.ui.review.ReviewListView;

public class SideNavbarLayout extends AppLayout {

    public SideNavbarLayout() {
        H1 title = new H1("Mat Zip");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        addToDrawer(getTabs());
        addToNavbar(new DrawerToggle(), title, darkModeToggleButton());
        setPrimarySection(Section.DRAWER);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(
                createTab(VaadinIcon.HOME, null, MainView.class),
                createTab(VaadinIcon.USER, "유저", MemberListView.class),
                createTab(VaadinIcon.SHOP, "음식점", RestaurantListView.class),
                createTab(VaadinIcon.TEXT_INPUT, "리뷰", ReviewListView.class)
        );
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(final VaadinIcon viewIcon, final String viewName,
                          final Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink();
        link.setRoute(navigationTarget);
        link.add(createIcon(viewIcon), new Span(viewName));
        return new Tab(link);
    }

    private Icon createIcon(final VaadinIcon viewIcon) {
        Icon icon = viewIcon.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");
        return icon;
    }

    private Button darkModeToggleButton() {
        Button button = new Button(VaadinIcon.ADJUST.create(), click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();

            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return button;
    }
}
