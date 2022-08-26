package com.woowacourse.matzip.ui.member;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
                createMemberGrid()
        );
    }

    private Grid<Member> createMemberGrid() {
        Grid<Member> grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(Member::getId).setHeader("id");
        grid.addColumn(Member::getGithubId).setHeader("github id");
        grid.addColumn(Member::getUsername).setHeader("username");
        grid.addColumn(Member::getProfileImage).setHeader("profile image url");
        grid.addColumn(Member::getCreatedAt).setHeader("created date");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.setItems(adminMemberService.findAll());
        return grid;
    }
}
