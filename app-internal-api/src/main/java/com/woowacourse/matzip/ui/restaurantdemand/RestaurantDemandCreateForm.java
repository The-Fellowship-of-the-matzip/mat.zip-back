package com.woowacourse.matzip.ui.restaurantdemand;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.woowacourse.matzip.domain.campus.Campus;
import com.woowacourse.matzip.domain.category.Category;
import java.util.List;

public class RestaurantDemandCreateForm extends FormLayout {

    private final ComboBox<Category> category = new ComboBox<>("category");
    private final ComboBox<Campus> campus = new ComboBox<>("campus");
    private final TextField name = new TextField("name");
    private final TextField address = new TextField("address");
    private final NumberField distance = new NumberField("distance");
    private final TextField kakaoMapUrl = new TextField("kakaoMapUrl");
    private final TextField imageUrl = new TextField("imageUrl");

    private final Button save = new Button("save");
    private final Button close = new Button("cancel");

    public RestaurantDemandCreateForm(List<Category> categories, List<Campus> campuses) {
        addClassName("restaurant-demand-create-form");

        category.setItems(categories);
        category.setItemLabelGenerator(Category::getName);
        campus.setItems(campuses);
        campus.setItemLabelGenerator(Campus::getName);

        add(
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

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, close);
    }
}
