package com.woowacourse.matzip.domain.campus;

import com.woowacourse.matzip.exception.CampusNotFoundException;
import java.util.List;

public class CampusCacheRepository {

    private final List<Campus> campuses;

    public CampusCacheRepository(final List<Campus> campuses) {
        this.campuses = campuses;
    }

    public void checkExistId(final Long id) {
        if (notContainsSameId(id)) {
            throw new CampusNotFoundException();
        }
    }

    private boolean notContainsSameId(final Long id) {
        return campuses.stream()
                .noneMatch(campus -> campus.isSameId(id));
    }

    public List<Campus> getCampuses() {
        return List.copyOf(campuses);
    }
}
