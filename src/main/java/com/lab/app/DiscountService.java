package com.lab.app;

public class DiscountService {

  public int calculateDiscount(boolean vip, boolean bigPurchase) {
    if (vip && bigPurchase) return 15;
    if (vip) return 10;
    if (bigPurchase) return 5;
    return 0;
  }


}
