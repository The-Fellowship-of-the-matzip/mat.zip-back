package com.woowacourse.matzip.ui.restaurantdemand;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class RestaurantDemandCreateForm extends FormLayout {

    private final TextField requestId = new TextField("requestId");
    private final ComboBox<Category> category = new ComboBox<>("category");
    private final ComboBox<Campus> campus = new ComboBox<>("campus");
    private final TextField name = new TextField("name");
    private final TextField address = new TextField("address");
    private final IntegerField distance = new IntegerField("distance");
    private final TextField kakaoMapUrl = new TextField("kakaoMapUrl");
    private final TextField imageUrl = new TextField("imageUrl");

    private final Button save = new Button("save");
    private final Button close = new Button("cancel");

    private final Binder<RestaurantDemandRequest> requestBinder = new BeanValidationBinder<>(
            RestaurantDemandRequest.class);

    private RestaurantDemandRequest restaurantDemandRequest;

    public RestaurantDemandCreateForm(List<Category> categories, List<Campus> campuses) {
        addClassName("restaurant-demand-create-form");

        category.setItems(categories);
        category.setItemLabelGenerator(Category::getName);
        campus.setItems(campuses);
        campus.setItemLabelGenerator(Campus::getName);

        requestBinder.forField(requestId)
                .withConverter(Long::valueOf, String::valueOf)
                .bind(RestaurantDemandRequest::getRequestId, RestaurantDemandRequest::setRequestId);
        requestId.setReadOnly(true);
        requestBinder.bind(name, RestaurantDemandRequest::getName, RestaurantDemandRequest::setName);

        add(
                requestId,
                category,
                campus,
                name,
                address,
                distance,
                kakaoMapUrl,
                imageUrl,
                createButtonsLayout()
        );
    }

    public void setRestaurantDemand(final Long id, final String name) {
        clearAllField();
        RestaurantDemandRequest restaurantDemandRequest = new RestaurantDemandRequest(id, name);
        this.restaurantDemandRequest = restaurantDemandRequest;
        requestBinder.readBean(restaurantDemandRequest);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> acceptRestaurantDemand());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, close);
    }

    private void acceptRestaurantDemand() {
        Restaurant restaurant = Restaurant.builder()
                .categoryId(category.getValue().getId())
                .campusId(campus.getValue().getId())
                .name(name.getValue())
                .address(address.getValue())
                .distance(distance.getValue())
                .kakaoMapUrl(kakaoMapUrl.getValue())
                .imageUrl(imageUrl.getValue())
                .build();

        fireEvent(new SaveEvent(this, restaurant, restaurantDemandRequest.getRequestId()));
    }

    private void clearAllField() {
        requestId.clear();
        category.clear();
        campus.clear();
        name.clear();
        address.clear();
        distance.clear();
        kakaoMapUrl.clear();
        imageUrl.clear();
    }

    // Events
    public static abstract class RestaurantDemandCreateFormEvent extends ComponentEvent<RestaurantDemandCreateForm> {

        private final Restaurant restaurant;
        private final Long restaurantDemandId;

        protected RestaurantDemandCreateFormEvent(final RestaurantDemandCreateForm source,
                                                  final Restaurant restaurant, final Long restaurantDemandId) {
            super(source, false);
            this.restaurant = restaurant;
            this.restaurantDemandId = restaurantDemandId;
        }

        public Restaurant getRestaurant() {
            return restaurant;
        }

        public Long getRestaurantDemandId() {
            return restaurantDemandId;
        }
    }

    public static class SaveEvent extends RestaurantDemandCreateFormEvent {
        SaveEvent(final RestaurantDemandCreateForm source, final Restaurant restaurant, final Long restaurantDemandId) {
            super(source, restaurant, restaurantDemandId);
        }
    }

    public static class CloseEvent extends RestaurantDemandCreateFormEvent {
        CloseEvent(final RestaurantDemandCreateForm source) {
            super(source, null, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(final Class<T> eventType,
                                                                  final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @Getter
    @Setter
    private static class RestaurantDemandRequest {

        private Long requestId;
        private String name;

        private RestaurantDemandRequest() {
        }

        public RestaurantDemandRequest(final Long requestId, final String name) {
            this.requestId = requestId;
            this.name = name;
        }
    }
}
