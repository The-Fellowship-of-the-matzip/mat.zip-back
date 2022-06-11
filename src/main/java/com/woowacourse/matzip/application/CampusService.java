package com.woowacourse.matzip.application;

import com.woowacourse.matzip.application.response.CampusResponse;
import com.woowacourse.matzip.domain.campus.CampusCacheRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CampusService {

    private final CampusCacheRepository campusCacheRepository;

    public CampusService(final CampusCacheRepository campusCacheRepository) {
        this.campusCacheRepository = campusCacheRepository;
    }

    public List<CampusResponse> findAll() {
        return campusCacheRepository.getCampuses()
                .stream()
                .map(CampusResponse::from)
                .collect(Collectors.toList());
    }
}
