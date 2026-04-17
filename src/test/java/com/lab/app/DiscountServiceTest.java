package com.lab.app;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DiscountServiceTest {

  DiscountService discountService = new DiscountService();

  @Test
  void case1_noVip_noBigPurchase() {
    assertEquals(0, discountService.calculateDiscount(false, false));
  }

  @Test
  void case2_noVip_bigPurchase() {
    assertEquals(5, discountService.calculateDiscount(false, true));
  }

  @Test
  void case3_vip_noBigPurchase() {
    assertEquals(10, discountService.calculateDiscount(true, false));
  }

  @Test
  void case4_vip_bigPurchase() {
    assertEquals(15, discountService.calculateDiscount(true, true));
  }

}