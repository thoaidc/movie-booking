package vn.ptit.moviebooking.booking.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.ptit.moviebooking.booking.constants.WebSocketConstants;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToTopic(String topic, Object message) {
        messagingTemplate.convertAndSend(topic, message);
    }

    public void notifyAll(Object message) {
        messagingTemplate.convertAndSend(WebSocketConstants.Topic.DEFAULT, message);
    }
}
