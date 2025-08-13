package vn.ptit.moviebooking.booking.dto.request;

public class RefundRequestCommand extends BaseCommandDTO {

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
