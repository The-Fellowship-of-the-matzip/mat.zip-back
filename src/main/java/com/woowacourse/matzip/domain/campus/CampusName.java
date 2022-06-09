package com.woowacourse.matzip.domain.campus;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CampusName {

    JAMSIL("잠실"),
    SEOLLEUNG("선릉"),
    ;

    private final String value;

    CampusName(final String value) {
        this.value = value;
    }

    public static CampusName from(final String value) {
        return Arrays.stream(values())
                .filter(campusName -> campusName.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 캠퍼스 이름입니다."));
    }
}
