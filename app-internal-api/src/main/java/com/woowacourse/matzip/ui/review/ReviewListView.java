package com.woowacourse.matzip.ui.review;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Route;
import com.woowacourse.matzip.application.AdminReviewService;
import com.woowacourse.matzip.application.response.ReviewResponse;
import com.woowacourse.matzip.ui.SideNavbarLayout;

@Route(value = "/reviews", layout = SideNavbarLayout.class)
public class ReviewListView extends VerticalLayout {

    private final AdminReviewService adminReviewService;

    public ReviewListView(final AdminReviewService adminReviewService) {
        this.adminReviewService = adminReviewService;
        setSizeFull();

        add(
                createReviewGrid()
        );
    }

    private Grid<ReviewResponse> createReviewGrid() {
        Grid<ReviewResponse> grid = new Grid<>(ReviewResponse.class, false);
        grid.setSizeFull();

        grid.addColumn(ReviewResponse::getId).setHeader("id");
        grid.addColumn(ReviewResponse::getWriterName).setHeader("writer");
        grid.addColumn(ReviewResponse::getRestaurantName).setHeader("restaurant");
        grid.addColumn(ReviewResponse::getContent).setHeader("content");
        grid.addColumn(ReviewResponse::getRating).setHeader("rating");
        grid.addColumn(ReviewResponse::getMenu).setHeader("menu");
        grid.addColumn(new LocalDateTimeRenderer<>(ReviewResponse::getCreatedAt, "yyyy-MM-dd hh:mm:ss"))
                .setHeader("created date")
                .setComparator(ReviewResponse::getCreatedAt);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setSortable(true));

        grid.setItems(adminReviewService.findAll());
        return grid;
    }
}
