package com.woowacourse.job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ReviewFailoverDateJobParameter {

    private LocalDate date;

    public ReviewFailoverDateJobParameter(final LocalDate date) {
        this.date = date;
    }

    public ReviewFailoverDateJobParameter(final String date) {
        this(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
