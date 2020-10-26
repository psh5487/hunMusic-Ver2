package com.music.configure;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.music.event.EventExceptionHandler;
import com.music.event.JoinEventListener;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ConfigurationProperties(prefix = "eventbus")
@Getter
@Setter
public class EventConfigure {

    private int asyncPoolCore;
    private int asyncPoolMax;
    private int asyncPoolQueue;

    @Bean
    public TaskExecutor eventTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("EventBus-");
        executor.setCorePoolSize(asyncPoolCore);
        executor.setMaxPoolSize(asyncPoolMax);
        executor.setQueueCapacity(asyncPoolQueue);
        executor.afterPropertiesSet();
        return executor;
    }

    @Bean
    public EventExceptionHandler eventExceptionHandler() {
        return new EventExceptionHandler();
    }

    @Bean
    public EventBus eventBus(TaskExecutor eventTaskExecutor, EventExceptionHandler eventExceptionHandler) {
        return new AsyncEventBus(eventTaskExecutor, eventExceptionHandler);
    }

    @Bean(destroyMethod = "close")
    public JoinEventListener joinEventListener(EventBus eventBus, KafkaTemplate kafkaTemplate) {
        return new JoinEventListener(eventBus, kafkaTemplate);
    }

}
