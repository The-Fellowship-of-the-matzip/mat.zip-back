package com.woowacourse.matzip.repository;

import com.woowacourse.matzip.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
