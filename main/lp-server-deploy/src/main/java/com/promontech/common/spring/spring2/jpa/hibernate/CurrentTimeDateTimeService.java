package com.promontech.common.spring.spring2.jpa.hibernate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class CurrentTimeDateTimeService implements DateTimeService {

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
}
