package io.zeebe.exporter.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import io.camunda.zeebe.exporter.api.Exporter;
import io.camunda.zeebe.exporter.api.context.Context;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import org.slf4j.Logger;

import java.io.IOException;

public class RabbitMqExporter implements Exporter {

    private Logger logger;

    private Controller controller;

    private Channel channel;

    private ExporterConfiguration config;

    @Override
    public void configure(Context context) {
        logger = context.getLogger();
        config = context.getConfiguration().instantiate(ExporterConfiguration.class);

        logger.debug("Starting exporter with configuration: {}", config);

        final var filter = new RecordFilter(config);
        context.setFilter(filter);
    }

    @Override
    public void open(Controller controller) {
        this.controller = controller;
        channel = config.getRabbitMqChannel();
    }

    @Override
    public void export(Record<?> record) {
        if (channel != null) {
            try {
                channel.basicPublish("", config.getQueue(), MessageProperties.PERSISTENT_TEXT_PLAIN,
                        record.getValue().toJson().getBytes());
                logger.debug("Added a record to the rabbitMq '{}' queue : {}", config.getQueue(), record.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}