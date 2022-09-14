package com.woowacourse.matzip.ui.restaurantdemand;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.woowacourse.matzip.application.request.RestaurantCreateRequest;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import com.woowacourse.matzip.domain.restaurant.Restaurant;
import com.woowacourse.matzip.domain.restaurant.RestaurantRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class RestaurantDemandCreateForm extends FormLayout {

    private final TextField requestId = new TextField("requestId");
    private final ComboBox<Category> category = new ComboBox<>("category");
    private final ComboBox<Campus> campus = new ComboBox<>("campus");
    private final TextField name = new TextField("name");
    private final TextField address = new TextField("address");
    private final TextField distance = new TextField("distance");
    private final TextField kakaoMapUrl = new TextField("kakaoMapUrl");
    private final TextField imageUrl = new TextField("imageUrl");

    private final Button save = new Button("save");
    private final Button close = new Button("cancel");

    private final Binder<RestaurantDemandRequest> requestBinder = new BeanValidationBinder<>(RestaurantDemandRequest.class);
    private final Binder<RestaurantCreateRequest> demandBinder = new BeanValidationBinder<>(RestaurantCreateRequest.class);

    private Restaurant restaurant;

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

        demandBinder.bind(name, RestaurantCreateRequest::getName, RestaurantCreateRequest::setName);
        demandBinder.bind(address, RestaurantCreateRequest::getAddress, RestaurantCreateRequest::setAddress);
        demandBinder.forField(distance)
                .withConverter(Long::valueOf, String::valueOf)
                .bind(RestaurantCreateRequest::getDistance, RestaurantCreateRequest::setDistance);
        demandBinder.bind(kakaoMapUrl, RestaurantCreateRequest::getKakaoMapUrl, RestaurantCreateRequest::setKakaoMapUrl);
        demandBinder.bind(imageUrl, RestaurantCreateRequest::getImageUrl, RestaurantCreateRequest::setImageUrl);

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
        RestaurantDemandRequest request = new RestaurantDemandRequest(id, name);
        requestBinder.readBean(request);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

//        save.addClickListener(event -> validateAndSave());
//        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contact)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, close);
    }

//    private void validateAndSave() {
//        try {
//            requestBinder.writeBean(contact);
//            fireEvent(new SaveEvent(this, contact));
//        } catch (ValidationException e) {
//            e.printStackTrace();
//        }
//    }

    // Events
    public static abstract class RestaurantDemandCreateFormEvent extends ComponentEvent<RestaurantDemandCreateForm> {

        private Restaurant restaurant;

        protected RestaurantDemandCreateFormEvent(final RestaurantDemandCreateForm source,
                                                  final Restaurant restaurant) {
            super(source, false);
            this.restaurant = restaurant;
        }

        public Restaurant getContact() {
            return restaurant;
        }
    }

    public static class SaveEvent extends RestaurantDemandCreateFormEvent {
        SaveEvent(final RestaurantDemandCreateForm source, final Restaurant restaurant) {
            super(source, restaurant);
        }
    }

    public static class CloseEvent extends RestaurantDemandCreateFormEvent {
        CloseEvent(final RestaurantDemandCreateForm source) {
            super(source, null);
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
