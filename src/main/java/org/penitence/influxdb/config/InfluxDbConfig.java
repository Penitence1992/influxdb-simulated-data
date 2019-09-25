package org.penitence.influxdb.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.penitence.influxdb.property.InfluxDbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renjie
 * @since 1.0.0
 **/
@Configuration
public class InfluxDbConfig {

    @Autowired
    private InfluxDbProperties properties;

    @Bean
    public InfluxDB influxDB(){
        BatchPoints batchPoints = BatchPoints
                .database(properties.getDatabase())
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        return InfluxDBFactory.connect(properties.getHost())
                .setDatabase(properties.getDatabase());
    }
}
