package org.kubsu.tuning.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.kubsu.tuning.domain.TaskToConfigure;
import org.kubsu.tuning.domain.dto.TaskToCollectDto;
import org.kubsu.tuning.domain.dto.TaskToCollectResultDto;
import org.kubsu.tuning.domain.dto.TaskToConfigureResultDto;
import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:kafka.properties")
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    String bootstrapServers;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    String trustedPackages;

    @Value("${spring.kafka.consumer.group-id}")
    String groupId;

    @Bean
    ProducerFactory<String, TaskToCollectDto> producerFactoryTaskToCollectQueue() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    ProducerFactory<String, TaskToConfigure> producerFactoryTaskToConfigureQueue() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    ConsumerFactory<String, TaskToCollectResultDto> consumerFactoryTaskToCollectResult(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConsumerFactory<String, TaskToConfigureResultDto> consumerFactoryTaskToConfigureResult(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    NewTopic createTaskToCollectQueueTopic() {
        return TopicBuilder.name("task-to-collect-queue-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    NewTopic createTaskToCollectResultsTopic() {
        return TopicBuilder.name("task-to-collect-result-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    NewTopic createTaskToConfigureQueueTopic() {
        return TopicBuilder.name("task-to-configure-queue-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    NewTopic createTaskToConfigureResultsTopic() {
        return TopicBuilder.name("task-to-configure-result-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    KafkaTemplate<String, TaskToCollectDto> kafkaTemplateTaskToCollectQueue(ProducerFactory<String, TaskToCollectDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    KafkaTemplate<String, TaskToConfigure> kafkaTemplateTaskToConfigureQueue(ProducerFactory<String, TaskToConfigure> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
