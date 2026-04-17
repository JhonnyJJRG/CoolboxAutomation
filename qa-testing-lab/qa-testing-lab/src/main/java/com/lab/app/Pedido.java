package com.lab.app;

public class Pedido {

  private double total;
  private boolean isVip;

  public Pedido(double total, boolean isVip) {
    this.total = total;
    this.isVip = isVip;
  }

  public boolean puedoComprar() {
    return total > 0;
  }

  public double calcularTotal() {
    if (total <=0) return 0;
    double descuento = 0;
    if(isVip) {
      descuento = 0.2;
    } else if(total > 100) {
      descuento = 0.1;
    }
    return total * (1 - descuento);
  }
}
