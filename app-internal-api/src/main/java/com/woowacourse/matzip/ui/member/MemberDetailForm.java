package com.woowacourse.matzip.ui.member;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class MemberDetailForm extends FormLayout {

    public MemberDetailForm() {
        addClassName("contact-form");

        add(
                new H2("멤버 상세 정보"),
                new TextField("id"),
                new TextField("github id"),
                new TextField("username"),
                new TextField("profile image url"),
                new TextField("created date"),
                createButtonsLayout()
        );
    }

    private HorizontalLayout createButtonsLayout() {
        Button save = new Button("저장");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        Button delete = new Button("삭제");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button close = new Button("취소");
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addClickShortcut(Key.ESCAPE);


        return new HorizontalLayout(save, delete, close);
    }
}
