package org.penitence.influxdb.utils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author renjie
 * @since
 **/
public class RandomSupplierUtils {

    private static Random random = new Random();

    public static Supplier<Double> createRangeSupplier(int start, int end) {
        return () -> new BigDecimal(start + random.nextDouble() * (end - start)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
