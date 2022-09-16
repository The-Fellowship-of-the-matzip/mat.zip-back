package com.woowacourse.matzip.ui.restaurant;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.woowacourse.matzip.application.AdminRestaurantService;
import com.woowacourse.matzip.application.response.RestaurantResponse;

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

    private Grid<RestaurantResponse> createRestaurantGrid() {
        Grid<RestaurantResponse> grid = new Grid<>(RestaurantResponse.class, false);
        grid.setSizeFull();

        grid.addColumn(RestaurantResponse::getId).setHeader("id");
        grid.addColumn(RestaurantResponse::getCategoryName).setHeader("category");
        grid.addColumn(RestaurantResponse::getCampusName).setHeader("campus");
        grid.addColumn(RestaurantResponse::getName).setHeader("name");
        grid.addColumn(RestaurantResponse::getAddress).setHeader("address");
        grid.addColumn(RestaurantResponse::getDistance).setHeader("distance");
        grid.addColumn(RestaurantResponse::getKakaoMapUrl).setHeader("kakao map url");
        grid.addColumn(RestaurantResponse::getImageUrl).setHeader("image url");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setSortable(true));

        grid.setItems(adminRestaurantService.findAll());
        return grid;
    }
}
