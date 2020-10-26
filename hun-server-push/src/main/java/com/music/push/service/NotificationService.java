package com.music.push.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.event.PushSubscribedMessage;
import com.music.event.UserJoinedMessage;
import com.music.push.model.PushMessage;
import com.music.push.model.Subscription;
import com.music.push.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final PushService pushService;

    private final ObjectMapper objectMapper;

    private final SubscriptionRepository subscriptionRepository;

    @KafkaListener(topics = "${spring.kafka.topic.subscription-request}", containerFactory = "kafkaListenerContainerSubscriptionFactory")
    @SendTo
    public PushSubscribedMessage subscribe(PushSubscribedMessage pushSubscribedMessage) {
        Subscription subscription = new Subscription(
                pushSubscribedMessage.getId(),
                pushSubscribedMessage.getUserId(),
                pushSubscribedMessage.getNotificationEndpoint(),
                pushSubscribedMessage.getPublicKey(),
                pushSubscribedMessage.getAuth(),
                pushSubscribedMessage.getCreatedAt()
        );

        Subscription saved = subscriptionRepository.save(subscription);

        PushSubscribedMessage newPushSubscribedMessage = new PushSubscribedMessage(
                saved.getId(),
                saved.getUserId(),
                saved.getNotificationEndpoint(),
                saved.getPublicKey(),
                saved.getAuth(),
                saved.getCreatedAt()
        );

        return newPushSubscribedMessage;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user-joined}", containerFactory = "kafkaListenerContainerPushMessageFactory")
    public void notifyAll(UserJoinedMessage userJoinedMessage) throws Exception {
        String name = userJoinedMessage.getName();
        Long userId = userJoinedMessage.getUserId();

        PushMessage message = new PushMessage(name + " Joined!", userId + " friend", "Please send welcome!");

        List<Subscription> subscriptions = subscriptionRepository.findAll();

        for (Subscription subscription : subscriptions) {
            Notification notification = new Notification(
                    subscription.getNotificationEndpoint(),
                    subscription.getPublicKey(),
                    subscription.getAuth(),
                    objectMapper.writeValueAsBytes(message)
            );
            pushService.send(notification);
        }
    }
}
