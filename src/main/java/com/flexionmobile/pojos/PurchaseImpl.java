package com.flexionmobile.pojos;

import com.flexionmobile.codingchallenge.integration.Purchase;

/**
 * POJO for {@link Purchase}
 */
public class PurchaseImpl implements Purchase {

    private boolean consumed;
    private String id;
    private String itemId;

    /**
     * Default constructor for Gson
     */
    public PurchaseImpl() {}

    public PurchaseImpl(String id, boolean consumed, String itemId) {
        this.id = id;
        this.consumed = consumed;
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public boolean getConsumed() {
        return consumed;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "PurchaseImpl{" +
                "consumed=" + consumed +
                ", id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
