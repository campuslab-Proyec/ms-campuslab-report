package campuslab.ms_campuslab_report.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${campuslab.kafka.topic-dlt}")
    private String dltTopic;

    @Bean
    public NewTopic reportDlt() {
        return TopicBuilder.name(dltTopic)
                .partitions(3)
                .replicas(3)
                .config("retention.ms", "1209600000")
                .build();
    }
}