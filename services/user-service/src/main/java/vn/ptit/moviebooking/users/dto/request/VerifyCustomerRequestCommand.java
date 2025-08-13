package vn.ptit.moviebooking.users.dto.request;

public class VerifyCustomerRequestCommand {

    private int sagaId;
    private VerifyCustomerRequest verifyCustomerRequest;

    public int getSagaId() {
        return sagaId;
    }

    public void setSagaId(int sagaId) {
        this.sagaId = sagaId;
    }

    public VerifyCustomerRequest getVerifyCustomerRequest() {
        return verifyCustomerRequest;
    }

    public void setVerifyCustomerRequest(VerifyCustomerRequest verifyCustomerRequest) {
        this.verifyCustomerRequest = verifyCustomerRequest;
    }
}
