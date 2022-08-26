package com.woowacourse.matzip.ui.restaurant;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.woowacourse.matzip.application.AdminRestaurantService;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.ui.SideNavbarLayout;

@Route(value = "/restaurants", layout = SideNavbarLayout.class)
public class RestaurantListView extends VerticalLayout {

    private final AdminRestaurantService adminRestaurantService;

    public RestaurantListView(final AdminRestaurantService adminRestaurantService) {
        this.adminRestaurantService = adminRestaurantService;
        addClassName("restaurant-view");
        setSizeFull();

        add(
                createRestaurantGrid()
        );
    }

    private Grid<Restaurant> createRestaurantGrid() {
        Grid<Restaurant> grid = new Grid<>(Restaurant.class, false);
        grid.setSizeFull();

        grid.addColumn(Restaurant::getId).setHeader("id");
        grid.addColumn(Restaurant::getCategoryId).setHeader("category");
        grid.addColumn(Restaurant::getCampusId).setHeader("campus");
        grid.addColumn(Restaurant::getName).setHeader("name");
        grid.addColumn(Restaurant::getAddress).setHeader("address");
        grid.addColumn(Restaurant::getDistance).setHeader("distance");
        grid.addColumn(Restaurant::getKakaoMapUrl).setHeader("kakao map url");
        grid.addColumn(Restaurant::getImageUrl).setHeader("image url");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setSortable(true));

        grid.setItems(adminRestaurantService.findAll());
        return grid;
    }
}
