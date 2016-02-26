package com.flexionmobile.pojos;

import com.flexionmobile.codingchallenge.integration.Purchase;

import java.util.List;

/**
 * Wrapper class for Json deserialization
 */
public class Purchases {

    private List<Purchase> purchases;

    /**
     * Default constructor for Gson
     */
    public Purchases() {}

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
