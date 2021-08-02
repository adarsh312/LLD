package com.company.model;

import lombok.Getter;
import lombok.Setter;

public class User {

  @Getter private final String name;

  private final int age;

  private final char gender;

  @Getter private int id;

  @Getter @Setter private Vehicle vehicle;

  @Getter @Setter private Trip currentTrip;

  @Getter private boolean OfferingRide;

  public User(int id, String name, int age, char gender) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public void addVechicle(Vehicle v) {
    this.vehicle = v;
    this.OfferingRide = true;
  }

  public boolean canOfferRide() {
    return (this.vehicle != null && this.OfferingRide);
  }

  public void canOfferRide(boolean status) {
    this.OfferingRide = status;
  }
}
