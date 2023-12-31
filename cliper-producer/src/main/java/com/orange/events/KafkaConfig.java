package com.orange.events;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.orange.dto.CliperDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:instance-1:9093}")
    private String bootstrapServers;

    @Value(value = "${spring.kafka.security.protocol:SSL}")
    private String securityProtocol;

    @Value(value = "${spring.kafka.ssl.trust-store-location:/data/certs/truststore.jks}")
    private String trustStoreLocation;

    @Value(value = "${spring.kafka.ssl.trust-store-password:password}")
    private String trustStorePassword;

    @Value(value = "${spring.kafka.ssl.key-store-location:/data/certs/keystore.jks}")
    private String keyStoreLocation;

    @Value(value = "${spring.kafka.ssl.key-store-password:password}")
    private String keyStorePassword;

    @Value(value = "${spring.kafka.ssl.key-password:password}")
    private String keyPassword;


    public ProducerFactory<String, CliperDTO> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        props.put("security.protocol", securityProtocol);
        props.put("ssl.truststore.location", trustStoreLocation);
        props.put("ssl.truststore.password", trustStorePassword);
        props.put("ssl.keystore.location", keyStoreLocation);
        props.put("ssl.keystore.password", keyStorePassword);
        props.put("ssl.key.password", keyPassword);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, CliperDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public ConsumerFactory<String, CliperDTO> consumerFactory() {
        JsonDeserializer<CliperDTO> deserializer = new JsonDeserializer<>(CliperDTO.class);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put("security.protocol", securityProtocol);
        props.put("ssl.truststore.location", trustStoreLocation);
        props.put("ssl.truststore.password", trustStorePassword);
        props.put("ssl.keystore.location", keyStoreLocation);
        props.put("ssl.keystore.password", keyStorePassword);
        props.put("ssl.key.password", keyPassword);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CliperDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CliperDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
