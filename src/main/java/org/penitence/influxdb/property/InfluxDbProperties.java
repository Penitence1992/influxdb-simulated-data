package org.penitence.influxdb.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author renjie
 * @since
 **/
@ConfigurationProperties(prefix = "influx")
@Data
@Component
public class InfluxDbProperties {

    private String host;

    private String database;
}
