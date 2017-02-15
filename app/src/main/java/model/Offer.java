package model;


import java.util.Date;

public class Offer  implements BasicObject{

    public static final String PERCENT = "PERCENT";
    public static final String MONEY = "MONEY";

    private long id;
    private String manufacturerName;
    private String ownerName;
    private double customerDiscount;
    private double price;
    private Date startDate;
    private String description = "";
    private Date endDate;
    private String productName;
    private long productId;
    private String discountUnit;
    private boolean isFavorite;
    private boolean fromPush;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void update(BasicObject obj) {
        if (obj instanceof Offer) {
            Offer off = (Offer) obj;
            if (off.productId != 0) {
                productId = off.productId;
            }

            if (off.ownerName != null) {
                ownerName = off.ownerName;
            }
            if (off.manufacturerName != null) {
                manufacturerName = off.manufacturerName;
            }
            if (off.customerDiscount != 0) {
                customerDiscount = off.customerDiscount;
            }
            if (off.startDate != null) {
                startDate = off.startDate;
            }
            if (off.endDate != null) {
                endDate = off.endDate;
            }
            if (off.productName != null) {
                productName = off.productName;
            }
            if (off.discountUnit != null) {
                discountUnit = off.discountUnit;
            }
            isFavorite = off.isFavorite;
        }
    }


    public long getProductId() {
        return productId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFromPush() {
        return fromPush;
    }

    public void setFromPush(boolean fromPush) {
        this.fromPush = fromPush;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getDiscountUnit() {
        return discountUnit;
    }

    public double getCustomerDiscount() {
        return customerDiscount;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public String getProductName() {
        return productName;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob instanceof Offer) {
            Offer anotherOff = (Offer) ob;
            return anotherOff.getId() == id;
        }
        return false;
    }

    public enum DiscountUnit {
        PERCENT,
        MONEY
    }
}
