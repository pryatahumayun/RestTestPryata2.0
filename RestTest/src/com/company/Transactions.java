package com.company;

import java.time.LocalDate;

public class Transactions implements Comparable<Transactions> {

  private final LocalDate transactionDate;

  private final Double amount;

  public Transactions(LocalDate date, Double amount) {
    this.transactionDate = date;
    this.amount = amount;
  }

  public LocalDate getTransactionDate() {
    return transactionDate;
  }

  public Double getAmount() {
    return amount;
  }

  @Override
  public int compareTo(Transactions o) {
    return getTransactionDate().compareTo(o.getTransactionDate());
  }

  @Override
  public String toString() {
    return "Transactions{" + "transactionDate=" + transactionDate + ", amount=" + amount + '}';
  }

}
