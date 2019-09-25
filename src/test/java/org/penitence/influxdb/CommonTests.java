package org.penitence.influxdb;

import org.junit.Test;
import org.penitence.influxdb.domain.HumitureSensor;
import org.penitence.influxdb.generator.HumitureDataGenerator;
import org.penitence.influxdb.generator.HumitureFactor;
import org.penitence.influxdb.utils.RandomSupplierUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @author renjie
 * @since 1.0.0
 **/
public class CommonTests {

    private Supplier<Double> doubleSupplier = RandomSupplierUtils.createRangeSupplier(30, 40);

    @Test
    public void testTTT() {
        for (int i = 0; i < 100; i++) {
            System.out.println(doubleSupplier.get());
        }
    }

    @Test
    public void testGenerator() {
        long count = Duration.between(LocalDateTime.now().minusYears(1), LocalDateTime.now()).toMillis() / 1000 / 30;

        System.out.println(count);
//
//        System.out.println(LocalDateTime.now());
//        HumitureDataGenerator generator = new HumitureDataGenerator("00001");
//
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            LocalDateTime dateTime = LocalDateTime.now().minusSeconds(i * 5);
//            HumitureSensor sensor = generator.generate(HumitureFactor.build(dateTime));
//            System.out.println(sensor);
//        }
//        long end = System.currentTimeMillis();
//
//        System.out.println("耗时: " + (end - start));
    }

}
