package model;

import lombok.Data;

@Data
public class PurchaseHistory {

    private String UserId;
    private String WaybillNo;
    private String ParcelName;
    private String ParcelSize;
    private int ParcelFee;
}
