package io.zeebe.exporter.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class ExporterConfiguration {

    private static final String ENV_PREFIX = "ZEEBE_RABBITMQ_";

    public String getHost() { return getEnv("HOST").orElse("localhost"); }

    public int getPort() { return getEnv("PORT").map(Integer::parseInt).orElse(5672); }

    public String getUsername() { return getEnv("USERNAME").orElse("guest"); }

    public String getPassword() { return getEnv("PASSWORD").orElse("guest"); }

    public String getQueue() { return getEnv("QUEUE").orElse("zeebeRecords"); }

    public String getEnabledValueTypes() {
        return getEnv("ENABLED_VALUE_TYPES").orElse("");
    }

    public String getEnabledRecordTypes() {
        return getEnv("ENABLED_RECORD_TYPES").orElse("");
    }

    public Channel getRabbitMqChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(getHost());
        factory.setPort(getPort());
        factory.setUsername(getUsername());
        factory.setPassword(getPassword());
        try {
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            channel.queueDeclare(getQueue(), true, false, false, null);
            return channel;
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<String> getEnv(String name) {
        return Optional.ofNullable(System.getenv(ENV_PREFIX + name));
    }

    @Override
    public String toString() {
        return "[port="
                + getPort()
                + ", host="
                + getHost()
                + ", Queue="
                + getQueue()
                + ", enabledValueTypes="
                + getEnabledValueTypes()
                + ", enabledRecordTypes="
                + getEnabledRecordTypes()
                + "]";
    }
}