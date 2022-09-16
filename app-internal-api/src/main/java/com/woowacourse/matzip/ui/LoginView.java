package com.woowacourse.matzip.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.woowacourse.matzip.application.AdminAuthService;
import com.woowacourse.matzip.ui.member.MemberListView;
import com.woowacourse.matzip.ui.restaurant.RestaurantListView;
import com.woowacourse.matzip.ui.restaurantdemand.RestaurantDemandListView;
import com.woowacourse.matzip.ui.review.ReviewListView;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Route("/")
public class LoginView extends VerticalLayout implements BeforeEnterObserver, ComponentEventListener<LoginEvent> {

    private static final String LOGIN_SUCCESS_URL = "/main";

    private final LoginOverlay loginOverlay = new LoginOverlay();
    private final AdminAuthService adminAuthService;

    public LoginView(final AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginOverlay.addLoginListener(this);
        loginOverlay.setTitle("MatZip");
        loginOverlay.setDescription("MatZip Admin Page");
        loginOverlay.setForgotPasswordButtonVisible(false);

        add(loginOverlay);
        loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginOverlay.setError(true);
        }
    }

    @Override
    public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
        if (!adminAuthService.authenticate(loginEvent.getUsername(), loginEvent.getPassword())) {
            return;
        }
        getAuthorizedRoutes().forEach(this::addSessionScope);
        UI.getCurrent().getPage().setLocation(LOGIN_SUCCESS_URL);
    }

    private List<AuthorizedRoute> getAuthorizedRoutes() {
        List<AuthorizedRoute> routes = new ArrayList<>();

        routes.add(new AuthorizedRoute(LOGIN_SUCCESS_URL, MainView.class));
        routes.add(new AuthorizedRoute("/review", ReviewListView.class));
        routes.add(new AuthorizedRoute("/restaurant_demands", RestaurantDemandListView.class));
        routes.add(new AuthorizedRoute("/restaurants", RestaurantListView.class));
        routes.add(new AuthorizedRoute("/members", MemberListView.class));

        return routes;
    }

    @SuppressWarnings("unchecked")
    private void addSessionScope(final AuthorizedRoute authorizedRoute) {
        RouteConfiguration.forSessionScope()
                .setRoute(authorizedRoute.getRoute(), authorizedRoute.getView(), SideNavbarLayout.class);
    }

    @Getter
    private static class AuthorizedRoute {

        private final String route;
        private final Class<? extends Component> view;

        public AuthorizedRoute(final String route, final Class<? extends Component> view) {
            this.route = route;
            this.view = view;
        }
    }
}
