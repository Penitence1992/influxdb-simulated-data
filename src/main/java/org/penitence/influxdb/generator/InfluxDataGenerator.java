package org.penitence.influxdb.generator;

/**
 * @author renjie
 * @since 1.0.0
 **/
public interface InfluxDataGenerator<T, F extends GenerateFactor> {

    T generate(F factor);
}
