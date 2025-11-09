package com.cultdata.report.api.support.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListComparisonUtil {

    public static <T> boolean areListsEqual(List<T> list1, List<T> list2) {
        if (list1 == list2) return true;
        if (list1 == null || list2 == null || list1.size() != list2.size()) return false;

        // Use frequency maps to handle duplicates correctly
        Map<T, Long> freqMap1 = list1.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<T, Long> freqMap2 = list2.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return freqMap1.equals(freqMap2);
    }

}
