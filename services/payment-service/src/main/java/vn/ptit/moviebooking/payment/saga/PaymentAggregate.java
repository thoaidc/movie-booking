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
    public PaymentAggregate(Command.CreatePaymentCommand cmd) {
        Event.CreatePaymentEvent createPaymentEvent = new Event.CreatePaymentEvent();
        BeanUtils.copyProperties(cmd, createPaymentEvent);
        AggregateLifecycle.apply(createPaymentEvent);
        log.debug("Payment created!, send command: {}", cmd.getPaymentId());
    }

    @CommandHandler
    public void handle(Command.ConfirmPaymentCommand cmd) {
        Event.ConfirmPaymentEvent confirmPaymentEvent = new Event.ConfirmPaymentEvent();
        BeanUtils.copyProperties(cmd, confirmPaymentEvent);
        AggregateLifecycle.apply(confirmPaymentEvent);
        log.debug("Payment success, send confirm command: {}", cmd.getPaymentId());
    }

    @CommandHandler
    public void handle(Command.RefundCommand cmd) {
        Event.RefundEvent refundEvent = new Event.RefundEvent();
        BeanUtils.copyProperties(cmd, refundEvent);
        AggregateLifecycle.apply(refundEvent);
        log.debug("Payment failed, send refund command: {}", cmd.getPaymentId());
    }

    @EventSourcingHandler
    public void on(Event.CreatePaymentEvent event) {
        this.paymentId = event.getPaymentId();
        log.debug("Payment created event: {}", event.getPaymentId());
    }

    @EventSourcingHandler
    public void on(Event.ConfirmPaymentEvent event) {
        this.paymentId = event.getPaymentId();
        log.debug("Payment success! Confirm event: {}", event.getPaymentId());
    }

    @EventSourcingHandler
    public void on(Event.RefundEvent event) {
        this.paymentId = event.getPaymentId();
        log.debug("Payment failed! Refund event: {}", event.getPaymentId());
    }
}
