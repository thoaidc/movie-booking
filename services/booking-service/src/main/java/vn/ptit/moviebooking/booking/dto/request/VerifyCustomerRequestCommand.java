package vn.ptit.moviebooking.booking.dto.request;

public class VerifyCustomerRequestCommand extends BaseCommandDTO {

    private VerifyCustomerRequest verifyCustomerRequest;

    public VerifyCustomerRequest getVerifyCustomerRequest() {
        return verifyCustomerRequest;
    }

    public void setVerifyCustomerRequest(VerifyCustomerRequest verifyCustomerRequest) {
        this.verifyCustomerRequest = verifyCustomerRequest;
    }
}
