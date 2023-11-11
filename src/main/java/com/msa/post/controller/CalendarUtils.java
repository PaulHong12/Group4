package com.msa.post.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarUtils {

    public static Map<String, List<Integer>> generateCalendarData() {
        Map<String, List<Integer>> calendarData = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            calendarData.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(date.getDayOfMonth());
        }

        return calendarData;
    }
}