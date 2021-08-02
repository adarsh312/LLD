package com.company.model;

import lombok.Getter;

import java.util.UUID;
import java.util.*;

public class Trip {
  @Getter private String id;

  @Getter private Vehicle vehicle;
  @Getter private List<User> rider = new LinkedList<>();

  @Getter private User driver;

  @Getter private String origin;
  @Getter private String destination;
  private int seats;

  @Getter private TripStatus status;

  public Trip(
      final User driver,
      final String origin,
      final String destination,
      final int seats
      ) {
    this.id = UUID.randomUUID().toString();
    this.driver = driver;
    this.origin = origin;
    this.destination = destination;
    this.seats = seats;
//    this.vehicle = driver.getVehicle();
  }

  public void updateTrip(final User driver,
      final String origin, final String destination, final int seats) {
    this.driver = driver;
    this.origin = origin;
    this.destination = destination;
    this.seats = seats;
  }

  public void addRiders(User rider) {
    this.rider.add(rider);
  }

  public void endTrip() {
    this.status = TripStatus.COMPLETED;
  }

  public void withdrawTrip() {
    this.status = TripStatus.WITHDRAWN;
  }
}
