package vn.ptit.moviebooking.notification.service.rabbitmq;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import vn.ptit.moviebooking.notification.constants.NotificationConstants;
import vn.ptit.moviebooking.notification.constants.RabbitMQConstants;
import vn.ptit.moviebooking.notification.dto.request.NotificationRequest;
import vn.ptit.moviebooking.notification.entity.Notification;
import vn.ptit.moviebooking.notification.service.NotificationService;

import java.util.Objects;

@Service
@DependsOn("bindingQueues")
public class RabbitMQConsumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final NotificationService notificationService;
    private final RabbitMQProducer rabbitMQProducer;

    public RabbitMQConsumer(NotificationService notificationService,
                            RabbitMQProducer rabbitMQProducer) {
        this.notificationService = notificationService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @RabbitListener(queues = RabbitMQConstants.Queue.NOTIFICATION)
    public void handleNotificationRequest(NotificationRequest notificationRequest, Channel channel, Message amqpMessage) {
        log.info("[BOOKING] - Notification confirm to customer: {}", notificationRequest.getReceiver());

        try {
            Notification notification = notificationService.sendNotification(notificationRequest);

            boolean isSentSuccess = Objects.nonNull(notification)
                    && Objects.equals(NotificationConstants.NotificationStatus.SUCCESS, notification.getStatus());
            log.info("[BOOKING] - Sent notification status: {}", isSentSuccess);
            rabbitMQProducer.confirmProcessed(channel, amqpMessage);
        } catch (Exception e) {
            log.error("Failed to execute command: {}", e.getMessage());
            rabbitMQProducer.notifyError(channel, amqpMessage, false);
        }
    }
}
