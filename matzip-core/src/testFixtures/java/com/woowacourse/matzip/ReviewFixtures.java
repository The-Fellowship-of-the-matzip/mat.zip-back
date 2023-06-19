package com.woowacourse.matzip;

import static com.woowacourse.matzip.MemberFixtures.HUNI;

import com.woowacourse.matzip.domain.member.Member;

public enum ReviewFixtures {

    REVIEW_1(HUNI.toMember(), "맛있네요 뽕쟁이", 4, "모듬족발(중)", "https://image.matzip.today/1.png");

    private final Member member;
    private final String content;
    private final int score;
    private final String menu;
    private final String imageUrl;

    ReviewFixtures(final Member member, final String content, final int score, final String menu, final String imageUrl) {
        this.member = member;
        this.content = content;
        this.score = score;
        this.menu = menu;
        this.imageUrl = imageUrl;
    }

    public Member getMember() {
        return member;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public String getMenu() {
        return menu;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
