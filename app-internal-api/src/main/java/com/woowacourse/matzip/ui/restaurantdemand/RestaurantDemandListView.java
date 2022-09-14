package com.woowacourse.matzip.ui.restaurantdemand;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.woowacourse.matzip.application.AdminRestaurantDemandService;
import com.woowacourse.matzip.application.response.RestaurantDemandResponse;
import com.woowacourse.matzip.repository.CampusRepository;
import com.woowacourse.matzip.repository.CategoryRepository;
import com.woowacourse.matzip.ui.SideNavbarLayout;

@Route(value = "/restaurant_demands", layout = SideNavbarLayout.class)
public class RestaurantDemandListView extends VerticalLayout {

    private final AdminRestaurantDemandService adminRestaurantDemandService;
    private final RestaurantDemandCreateForm restaurantDemandCreateForm;

    public RestaurantDemandListView(final AdminRestaurantDemandService adminRestaurantDemandService,
                                    final CategoryRepository categoryRepository,
                                    final CampusRepository campusRepository) {
        this.adminRestaurantDemandService = adminRestaurantDemandService;
        this.restaurantDemandCreateForm = new RestaurantDemandCreateForm(categoryRepository.findAll(),
                campusRepository.findAll());
        restaurantDemandCreateForm.setWidth("25em");
        restaurantDemandCreateForm
                .addListener(RestaurantDemandCreateForm.CloseEvent.class, e -> closeRestaurantCreateEditor());
        addClassName("list-view");
        setSizeFull();

        add(
                getMainPageContent()
        );
        closeRestaurantCreateEditor();
    }

    private Component getMainPageContent() {
        Grid<RestaurantDemandResponse> grid = createRestaurantDemandGrid();
        HorizontalLayout content = new HorizontalLayout(grid, restaurantDemandCreateForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, restaurantDemandCreateForm);
        content.addClassNames("main-page-content");
        content.setSizeFull();
        return content;
    }

    private Grid<RestaurantDemandResponse> createRestaurantDemandGrid() {
        Grid<RestaurantDemandResponse> grid = new Grid<>(RestaurantDemandResponse.class, false);
        grid.setSizeFull();

        grid.addColumn(RestaurantDemandResponse::getId).setHeader("id");
        grid.addColumn(RestaurantDemandResponse::getCategoryName).setHeader("category");
        grid.addColumn(RestaurantDemandResponse::getCampusName).setHeader("campus");
        grid.addColumn(RestaurantDemandResponse::getName).setHeader("request restaurant name");
        grid.addColumn(createMemberProfileRenderer()).setHeader("username");
        grid.addComponentColumn(response -> {
            if (response.isRegistered()) {
                return VaadinIcon.CHECK_CIRCLE.create();
            }
            return new Span();
        }).setHeader("is registered");

//        TODO : when add create_at in RestaurantDemand
//        grid.addColumn(new LocalDateTimeRenderer<>(Member::getCreatedAt, "yyyy-MM-dd hh:mm:ss"))
//                .setHeader("created date")
//                .setComparator(Member::getCreatedAt);

        grid.addItemClickListener(event -> viewNewCreateRestaurant(event.getItem()));

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setSortable(true));

        grid.setItems(adminRestaurantDemandService.findAll());
        return grid;
    }

    private Renderer<RestaurantDemandResponse> createMemberProfileRenderer() {
        return LitRenderer.<RestaurantDemandResponse>of(
                "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                        + "  <vaadin-avatar img=\"${item.profileImage}\" name=\"${item.username}\"></vaadin-avatar>"
                        + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                        + "    <span> ${item.username} </span>"
                        + "  </vaadin-vertical-layout>"
                        + "</vaadin-horizontal-layout>")
                .withProperty("profileImage", value -> value.getMember().getProfileImage())
                .withProperty("username", value -> value.getMember().getUsername());
    }

    public void viewNewCreateRestaurant(final RestaurantDemandResponse restaurantDemandResponse) {
        if (restaurantDemandResponse == null) {
            closeRestaurantCreateEditor();
        }
        assert restaurantDemandResponse != null;
        if (restaurantDemandResponse.isRegistered()) {
            Notification dd = Notification.show("dd", 5000, Position.TOP_CENTER);
            dd.addThemeVariants(NotificationVariant.LUMO_ERROR);
            closeRestaurantCreateEditor();
            return;
        }
        restaurantDemandCreateForm
                .setRestaurantDemand(restaurantDemandResponse.getId(), restaurantDemandResponse.getName());
        restaurantDemandCreateForm.setVisible(true);
        addClassName("editing");
    }

    private void closeRestaurantCreateEditor() {
        restaurantDemandCreateForm.setRestaurantDemand(null, null);
        restaurantDemandCreateForm.setVisible(false);
        removeClassName("editing");
    }
}
