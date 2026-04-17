package com.lab.app;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PedidoTest {

  @Test
  public void noDebePermitirComprarConTotalCero() {
    Pedido pedido = new Pedido(0, false);
    Assert.assertFalse(pedido.puedoComprar());
  }

  @Test
  public void debeAplicarDescuentoSiMontoMayor100() { //Descuento 10%
    Pedido pedido = new Pedido(200, false);
    Assert.assertEquals(pedido.calcularTotal(), 180);
  }

  @Test
  public void debeAplicarDescuentoVIP() { //Descuento 20%
    Pedido pedido = new Pedido(200, true);
    Assert.assertEquals(pedido.calcularTotal(), 160);
  }


}
