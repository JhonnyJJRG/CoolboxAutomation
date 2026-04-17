package com.lab.app;

public class PaymentService {

  public boolean procesarPago(double monto) {
    if (monto <= 0) return false;
    if (monto > 1000) return false;
    return true;
  }

  public boolean canBuy(boolean hasBalance,
                               boolean verified,
                               boolean blocked) {
    return hasBalance && verified && !blocked;
  }

  public int calculate(int x) {
    int result = 0;
    if(x > 10 && x <= 20 && x % 2 == 0) {
      result = 5;
    } else if(x > 20 && x <= 30) {

    } else if(x > 30 && x <= 40) {
      result = 15;
    } else if(x > 40) {
      result = 20;
    } else {
      result = 0;
    }
    return result;
  }

  public int sum(int[] numbers) {
    int total = 0;// [10,20,30]
    for (int num : numbers) {
      total += num;
    }
    return total;
  }

  public double calcularTotal(double precio, int cantidad) throws Exception {
    if(precio <= 0) throw new Exception("Error");
    calculate(10);
    return precio * cantidad;
  }

}
