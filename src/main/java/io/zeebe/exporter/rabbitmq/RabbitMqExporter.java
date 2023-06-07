package io.zeebe.exporter.rabbitmq;

import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.protocol.record.Record;
import org.slf4j.Logger;

public class RabbitMqExporter implements Exporter {

    private Logger logger;

    @Override
    public void configure(Context context) {
        logger = context.getLogger();
        logger.debug("Starting RabbitMQ Exporter ...");
    }

    @Override
    public void export(Record<?> record) {
        logger.debug("Record : {}", record);
    }
}