package org.penitence.influxdb;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.penitence.influxdb.domain.HumitureSensor;
import org.penitence.influxdb.generator.HumitureDataGenerator;
import org.penitence.influxdb.generator.HumitureFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@EnableConfigurationProperties
@SpringBootApplication
public class InfluxdbApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(InfluxdbApplication.class, args);
    }

    @Autowired
    private InfluxDB influxDB;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        influxDB.enableBatch(BatchOptions.DEFAULTS);
        long count = Duration.between(LocalDateTime.now().minusYears(1), LocalDateTime.now()).toMillis() / 1000 / 30;
        ZoneId zoneId =ZoneId.systemDefault();
        IntStream.range(1, 5)
                .parallel()
                .forEach(index -> {
                    String sensorNo = "H000" + index;
                    HumitureDataGenerator generator = new HumitureDataGenerator(sensorNo);
                    for (int i = 0; i < count; i++) {
                        LocalDateTime time = LocalDateTime.now().minusSeconds(i * 30);
                        HumitureSensor sensor = generator.generate(HumitureFactor.build(time));
                        influxDB.write(
                                Point.measurementByPOJO(HumitureSensor.class)
                                        .time(time.atZone(zoneId).toEpochSecond(), TimeUnit.SECONDS)
                                        .addFieldsFromPOJO(sensor)
                                        .build()
                        );
                    }
                });
        influxDB.close();
    }
}
