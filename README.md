# RabbitMQ Zeebe Exporter

This project provides a RabbitMQ exporter for Zeebe, allowing you to export [Zeebe](https://github.com/camunda-cloud/zeebe) records data to [RabbitMQ](https://www.rabbitmq.com/) queues.

## Usage

The Zeebe Exporter is a component or tool that allows you to export data from Zeebe, a workflow engine, to external systems or services. The usage of a Zeebe Exporter depends on your specific requirements and integration needs. Here are a few common use cases

1. **Logging and Monitoring**: You can use a Zeebe Exporter to export workflow execution data and events to a logging or monitoring system. This allows you to track the progress of workflows, monitor performance metrics.

2. **Integration with External Systems**: Integrate Zeebe with other systems or services in your architecture, an exporter can be used to export relevant workflow data to those systems.

3. **Data Analysis and Reporting**: Exporting data from Zeebe to a data analysis or reporting tool allows you to perform advanced analytics on workflow execution data.

4. **Data Synchronization**: Synchronize data between Zeebe and external systems (Real-time tracking).

## Install

### Docker

For a local setup, the repository contains a [docker-compose file](docker/docker-compose.yml). It starts a Zeebe broker with the rabbitMQ exporter.

```
mvn clean install
cd docker
docker-compose up
```

### Manual

1. Download the latest [Zeebe distribution](https://github.com/camunda-cloud/zeebe/releases) `zeebe-distribution-%{VERSION}.tar.gz`
2. Build JAR of RabbitMQ Zeebe Exporter `mvn clean install`
3. Download & start [RabbitMQ](https://www.rabbitmq.com/download.html) server
4. Copy the exporter JAR into the broker folder `~/zeebe-broker-%{VERSION}/exporters`
    ```
    cp target/zeebe-rabbitmq-exporter-1.0-SNAPSHOT.jar ~/zeebe-broker-%{VERSION}/exporters/
    ```
5. Add the exporter to the broker configuration `~/zeebe-broker-%{VERSION}/config/application.yaml`:
    ```
    zeebe:
      broker:  
        exporters:
          rabbitmq:
            className: io.zeebe.exporter.rabbitmq.RabbitMqExporter
            jarPath: exporters/zeebe-rabbitmq-exporter-1.0-SNAPSHOT.jar
    ```
6. Edit RabbitMQ configurations (host, username, and password) in the Zeebe Exporter environment variables.
7. Start the broker `~/zeebe-broker-%{VERSION}/bin/broker`

### Configuration

In the Zeebe configuration file, you can change

* the RabbitMQ host & port
* the RabbitMQ username & password
* the RabbitMQ queue name
* the value and record types which are exported

Default values:

```
- ZEEBE_RABBITMQ_HOST=localhost
- ZEEBE_RABBITMQ_PORT=5672
- ZEEBE_RABBITMQ_USERNAME=guest
- ZEEBE_RABBITMQ_PASSWORD=guest
- ZEEBE_RABBITMQ_QUEUE=zeebe
```

## Build it from Source

The exporter can be built with Maven

`mvn clean install`

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please create a new issue or submit a pull request.

