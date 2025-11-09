package com.cultdata.report.api.support.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
    public static double formatDecimal(double value, int scale) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
