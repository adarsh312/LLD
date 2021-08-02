package com.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Vehicle {

  private String number;
  private String vehicleName;
  private int maxSeatSize;

  public Vehicle(String num, String vehicleName, int maxSeatSize) {
    this.number = num;
    this.vehicleName = vehicleName;
    this.maxSeatSize = maxSeatSize;
  }
}
