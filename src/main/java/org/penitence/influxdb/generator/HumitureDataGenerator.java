package org.penitence.influxdb.generator;

import org.penitence.influxdb.domain.HumitureSensor;
import org.penitence.influxdb.utils.RandomSupplierUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author renjie
 * @since 1.0.0
 **/
public class HumitureDataGenerator implements InfluxDataGenerator<HumitureSensor, GenerateFactor<LocalDateTime>> {

    private final Random random = new Random();

    private Supplier<Double>[] springTemperature;
    private Supplier<Double>[] summerTemperature;
    private Supplier<Double>[] autumnTemperature;
    private Supplier<Double>[] winterTemperature;

    private Supplier<Double>[] springHumidity;
    private Supplier<Double>[] summerHumidity;
    private Supplier<Double>[] autumnHumidity;
    private Supplier<Double>[] winterHumidity;

    private final String sensorNo;

    public HumitureDataGenerator(String sensorNo) {
        initTemperature();
        initHumidity();
        this.sensorNo = sensorNo;
    }

    private void initTemperature() {
        Supplier<Double> spring = RandomSupplierUtils.createRangeSupplier(20, 30);
        Supplier<Double> summer = RandomSupplierUtils.createRangeSupplier(30, 40);
        Supplier<Double> autumn = RandomSupplierUtils.createRangeSupplier(10, 20);
        Supplier<Double> winter = RandomSupplierUtils.createRangeSupplier(0, 10);
        springTemperature = generatorRandom(80, spring, 20, summer);
        summerTemperature = generatorRandom(60, summer, 40, spring);
        autumnTemperature = generatorRandom(60, autumn, 40, summer);
        winterTemperature = generatorRandom(70, winter, 30, autumn);
    }

    private void initHumidity() {
        Supplier<Double> spring = RandomSupplierUtils.createRangeSupplier(50, 70);
        Supplier<Double> summer = RandomSupplierUtils.createRangeSupplier(70, 90);
        Supplier<Double> autumn = RandomSupplierUtils.createRangeSupplier(30, 50);
        Supplier<Double> winter = RandomSupplierUtils.createRangeSupplier(10, 30);
        springHumidity = generatorRandom(80, spring, 20, summer);
        summerHumidity = generatorRandom(60, summer, 40, spring);
        autumnHumidity = generatorRandom(60, autumn, 40, summer);
        winterHumidity = generatorRandom(70, winter, 30, autumn);
    }

    @Override
    public HumitureSensor generate(GenerateFactor<LocalDateTime> factor) {
        LocalDateTime instant = factor.get();
        Supplier<Double>[] suppliersTemp;
        Supplier<Double>[] suppliersHu;
        int month = instant.getMonthValue();
        if (month >= 3 && month <= 5) {
            suppliersTemp = springTemperature;
            suppliersHu = springHumidity;
        } else if (month >= 6 && month <= 8) {
            suppliersTemp = summerTemperature;
            suppliersHu = summerHumidity;
        } else if (month >= 9 && month <= 11) {
            suppliersTemp = autumnTemperature;
            suppliersHu = autumnHumidity;
        } else {
            suppliersTemp = winterTemperature;
            suppliersHu = winterHumidity;
        }
        return build(instant.atZone(ZoneId.systemDefault()).toInstant(), suppliersTemp, suppliersHu);
    }

    private Supplier<Double>[] generatorRandom(int probability1, Supplier<Double> supplier1, int probability2, Supplier<Double> supplier2) {
        Supplier[] randomArray = new Supplier[probability1 + probability2];
        int i = 0;
        for (; i < probability1; i++) {
            randomArray[i] = supplier1;
        }
        for (; i < probability2 + probability1; i++) {
            randomArray[i] = supplier2;
        }
        return randomArray;
    }

    private HumitureSensor build(Instant instant, Supplier<Double>[] temperature, Supplier<Double>[] humidity) {
        return HumitureSensor.builder()
                .humidity(find(humidity))
                .temperature(find(temperature))
                .sensorNo(sensorNo)
                .build();
    }

    private Double find(Supplier<Double>[] temperature) {
        int index = random.nextInt(100);
        return temperature[index].get();
    }
}
