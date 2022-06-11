package com.woowacourse.matzip.domain.campus;

import com.woowacourse.matzip.exception.CampusNotFoundException;
import java.util.List;

public class CampusCacheRepository {

    private final List<Campus> campuses;

    public CampusCacheRepository(final List<Campus> campuses) {
        this.campuses = campuses;
    }

    public Campus findById(final Long id) {
        return campuses.stream()
                .filter(campus -> campus.isSameId(id))
                .findFirst()
                .orElseThrow(CampusNotFoundException::new);
    }
}
