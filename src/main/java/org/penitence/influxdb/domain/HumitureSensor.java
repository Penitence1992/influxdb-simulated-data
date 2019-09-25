package org.penitence.influxdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * @author renjie
 * @since 1.0.0
 **/
@Measurement(name = "humiture")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HumitureSensor {

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "sensorNo", tag = true)
    private String sensorNo;

}
