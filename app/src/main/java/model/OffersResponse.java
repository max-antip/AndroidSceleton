package model;


import java.util.ArrayList;
import java.util.List;

public class OffersResponse extends Response{

    List<Offer> offers = new ArrayList<>();

    public OffersResponse() {
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
