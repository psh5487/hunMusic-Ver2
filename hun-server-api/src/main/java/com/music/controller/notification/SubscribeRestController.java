package com.music.controller.notification;

import com.music.controller.network.ApiResult;
import com.music.event.PushSubscribedMessage;
import com.music.model.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static com.music.controller.network.ApiResult.OK;

@RestController
@RequestMapping("api/auth/subscribe")
@RequiredArgsConstructor
public class SubscribeRestController {
    private static final Logger logger = LoggerFactory.getLogger(SubscribeRestController.class);

    @Value("${spring.kafka.topic.subscription-request}")
    private String requestTopic;

    @Value("${spring.kafka.topic.subscription-reply}")
    private String requestReplyTopic;

    private final ReplyingKafkaTemplate<String, PushSubscribedMessage, PushSubscribedMessage> kafkaTemplate;

    @PostMapping("")
    public ApiResult<PushSubscribedMessage> subscribe(@AuthenticationPrincipal User user,
                                                      @RequestBody PushSubscribedMessage subscription) throws ExecutionException, InterruptedException {

        PushSubscribedMessage pushSubscribedMessage = PushSubscribedMessage.builder()
                .id(subscription.getId())
                .notificationEndpoint(subscription.getNotificationEndpoint())
                .publicKey(subscription.getPublicKey())
                .auth(subscription.getAuth())
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .build();

        ProducerRecord<String, PushSubscribedMessage> record = new ProducerRecord<>(requestTopic, pushSubscribedMessage);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        RequestReplyFuture<String, PushSubscribedMessage, PushSubscribedMessage> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        ConsumerRecord<String, PushSubscribedMessage> consumerRecord = sendAndReceive.get();

        logger.info("success to subscribe {}", consumerRecord.value());

        return OK(consumerRecord.value());
    }

}
