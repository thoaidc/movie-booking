package vn.ptit.moviebooking.payment.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private static final Logger log = LoggerFactory.getLogger(PaymentAggregate.class);

    public PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(Command.ProcessPaymentCommand cmd) {
        Event.ProcessPaymentEvent processPaymentEvent = new Event.ProcessPaymentEvent();
        BeanUtils.copyProperties(cmd, processPaymentEvent);
        AggregateLifecycle.apply(processPaymentEvent);
        log.debug("Payment success!, send command: {}", cmd.getPaymentId());
    }

    @CommandHandler
    public void handle(Command.RefundCommand cmd) {
        Event.RefundEvent refundEvent = new Event.RefundEvent();
        BeanUtils.copyProperties(cmd, refundEvent);
        AggregateLifecycle.apply(refundEvent);
        log.debug("Payment failed, send command: {}", cmd.getPaymentId());
    }

    @EventSourcingHandler
    public void on(Event.ProcessPaymentEvent event) {
        this.paymentId = event.getPaymentId();
        log.debug("Payment success event: {}", event.getPaymentId());
    }

    @EventSourcingHandler
    public void on(Event.RefundEvent event) {
        this.paymentId = event.getPaymentId();
        log.debug("Payment failed! Refund event: {}", event.getPaymentId());
    }
}
