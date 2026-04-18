package com.lab.app;


import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PaymentServiceTest {

  PaymentService paymentService = new PaymentService();
  
  @Test
  void case1() {
    assertFalse(paymentService.canBuy(false, false, false));
  }

  @Test
  void case2() {
    assertFalse(paymentService.canBuy(false, false, true));
  }

  @Test
  void case3() {
    assertFalse(paymentService.canBuy(false, true, false));
  }

  @Test void case4() {
    assertFalse(paymentService.canBuy(false, true, true));
  }

  @Test void case5() {
    assertFalse(paymentService.canBuy(true, false, false));
  }

  @Test void case6() {
    assertFalse(paymentService.canBuy(true, false, true));
  }

  @Test void case7() {
    assertFalse(paymentService.canBuy(true, true, true));
  }

  @Test void case8_validPurchase() {
    assertTrue(paymentService.canBuy(true, true, false));
  }

  @Test
  void shouldNotProcessNegativeAmount() {
    assertFalse(paymentService.procesarPago(-1));
  }

  @Test
  void shouldNotProcessZeroAmount() {
    assertFalse(paymentService.procesarPago(0));
  }

  @Test
  void shouldProcessPositive1Amount() {
    assertTrue(paymentService.procesarPago(1));
  }

  @Test
  void shouldProcessPositiveAmount() {
    assertTrue(paymentService.procesarPago(1000));
  }

  @Test
  void shouldNotProcess1001Amount() {
    assertFalse(paymentService.procesarPago(1001));
  }

  @Test
  void testCalcularPrecio() throws Exception {
    double resultado = paymentService.calcularTotal(1, 3);
    assertEquals(resultado,3);
  }

  @Test(expectedExceptions = Exception.class)
  void testCalcularPrecioCero() throws Exception {
    paymentService.calcularTotal(-1, 3);
  }


}