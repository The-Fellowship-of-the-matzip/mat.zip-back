package com.woowacourse.matzip.domain.campus;

import com.woowacourse.matzip.exception.CampusNotFoundException;
import java.util.List;

public class CampusCacheRepository {

    private final List<Campus> campuses;

    public CampusCacheRepository(final List<Campus> campuses) {
        this.campuses = campuses;
    }

    public void checkExistId(final Long id) {
        campuses.stream()
                .filter(campus -> campus.isSameId(id))
                .findAny()
                .orElseThrow(CampusNotFoundException::new);
    }

    public List<Campus> getCampuses() {
        return List.copyOf(campuses);
    }
}
