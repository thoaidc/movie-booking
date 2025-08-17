package vn.ptit.moviebooking.booking.constants;

public interface RabbitMQConstants {

    interface RoutingKey {
        String NOTIFICATION = "routingKey.notifications";
    }

    interface Exchange {
        String DIRECT_EXCHANGE = "amq.direct";
    }
}
