package model;

import lombok.Data;

@Data
public class PurchaseHistory {

    private String UserId;
    private String billNo;
    private String flwOptName;
    private String flwOptSize;
    private int flwOptFee;
}
