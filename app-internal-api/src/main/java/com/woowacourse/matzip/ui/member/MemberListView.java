package com.woowacourse.matzip.ui.member;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.woowacourse.matzip.application.AdminMemberService;
import com.woowacourse.matzip.domain.member.Member;

@Route("/admin/members")
public class MemberListView extends VerticalLayout {

    private final AdminMemberService adminMemberService;

    public MemberListView(final AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
        addClassName("list-view");
        setSizeFull();

        add(
                getContent()
        );
    }

    private Component getContent() {
        Grid<Member> memberGrid = createMemberGrid();
        MemberDetailForm memberForm = createMemberForm();
        HorizontalLayout content = new HorizontalLayout(memberGrid, memberForm);
        content.setFlexGrow(2, memberGrid);
        content.setFlexGrow(1, memberForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private Grid<Member> createMemberGrid() {
        Grid<Member> grid = new Grid<>(Member.class, false);
        grid.setSizeFull();
        grid.addColumn(Member::getId).setHeader("id");
        grid.addColumn(Member::getGithubId).setHeader("github id");
        grid.addColumn(createMemberProfileRenderer()).setHeader("username");
        grid.addColumn(Member::getProfileImage).setHeader("profile image url");
        grid.addColumn(new LocalDateTimeRenderer<>(Member::getCreatedAt, "yyyy-MM-dd hh:mm:ss"))
                .setHeader("created date")
                .setComparator(Member::getCreatedAt);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setSortable(true));

        grid.setItems(adminMemberService.findAll());
        return grid;
    }

    private Renderer<Member> createMemberProfileRenderer() {
        return LitRenderer.<Member>of(
                "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                        + "  <vaadin-avatar img=\"${item.profileImage}\" name=\"${item.username}\"></vaadin-avatar>"
                        + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                        + "    <span> ${item.username} </span>"
                        + "  </vaadin-vertical-layout>"
                        + "</vaadin-horizontal-layout>")
                .withProperty("profileImage", Member::getProfileImage)
                .withProperty("username", Member::getUsername);
    }

    private MemberDetailForm createMemberForm() {
        MemberDetailForm memberDetailForm = new MemberDetailForm();
        memberDetailForm.setWidth("25em");
        return memberDetailForm;
    }
}
