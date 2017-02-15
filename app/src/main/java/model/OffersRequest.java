package model;


public class OffersRequest extends Request {

    public OffersRequest() {
        url = "http://dev.viaphone.us/api/customer/get-offers";
        resp = OffersResponse.class;
    }
}
