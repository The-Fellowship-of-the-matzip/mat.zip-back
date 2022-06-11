package com.woowacourse.matzip.presentation;

import com.woowacourse.matzip.domain.campus.CampusCacheRepository;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

public class CampusInterceptor implements HandlerInterceptor {

    private static final String CAMPUS_ID_PATH = "campusId";

    private final CampusCacheRepository campusCacheRepository;

    public CampusInterceptor(final CampusCacheRepository campusCacheRepository) {
        this.campusCacheRepository = campusCacheRepository;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        Long id = getCampusId(request);
        campusCacheRepository.checkExistId(id);
        return true;
    }

    private Long getCampusId(final HttpServletRequest request) {
        return (Long) getAttribute(request).get(CAMPUS_ID_PATH);
    }

    @SuppressWarnings("unchecked")
    private Map<String, ?> getAttribute(final HttpServletRequest request) {
        return (Map<String, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
