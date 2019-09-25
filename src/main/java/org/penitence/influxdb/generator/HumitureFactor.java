package org.penitence.influxdb.generator;

import java.time.LocalDateTime;

/**
 * @author renjie
 * @since 1.0.0
 **/
public class HumitureFactor implements GenerateFactor<LocalDateTime> {

    private final LocalDateTime instant;

    private HumitureFactor(LocalDateTime instant) {
        this.instant = instant;
    }

    @Override
    public LocalDateTime get() {
        return instant;
    }

    public static GenerateFactor<LocalDateTime> build(LocalDateTime instant) {
        return new HumitureFactor(instant);
    }
}
