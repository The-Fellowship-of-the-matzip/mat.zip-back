package com.woowacourse.matzip.application.response;

import com.woowacourse.matzip.domain.campus.Campus;
import lombok.Getter;

@Getter
public class CampusResponse {

    private Long id;
    private String name;

    private CampusResponse() {
    }

    private CampusResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static CampusResponse from(final Campus campus) {
        return new CampusResponse(campus.getId(), campus.getName());
    }
}
