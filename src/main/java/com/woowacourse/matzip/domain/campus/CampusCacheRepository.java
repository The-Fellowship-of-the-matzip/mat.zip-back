package com.woowacourse.matzip.domain.campus;

import com.woowacourse.matzip.exception.CampusNotFoundException;
import java.util.List;

public class CampusCacheRepository {

    private final List<Campus> campuses;

    public CampusCacheRepository(final List<Campus> campuses) {
        this.campuses = campuses;
    }

    public Campus findByCampusName(final String campusName) {
        return campuses.stream()
                .filter(campus -> campus.isSameCampusName(campusName))
                .findFirst()
                .orElseThrow(CampusNotFoundException::new);
    }
}
