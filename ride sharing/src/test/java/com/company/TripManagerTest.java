package com.company;

import com.company.exception.DriverNotFoundException;
import com.company.exception.InvalidRideParamException;
import com.company.exception.TripNotFoundException;
import com.company.exception.TripStatusException;
import com.company.manager.DriverManager;
import com.company.manager.RiderManager;
import com.company.manager.TripManager;
import com.company.model.User;
import com.company.model.Vehicle;
import com.company.model.Trip;
import com.company.model.TripStatus;
import com.company.strategy.OptimalDriverStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TripManagerTest {

  TripManager tripManager;
  User driver1, driver2;
  User rider1, rider2, rider3;

  @BeforeEach
  void setup() {

    driver1 = new User(1, "Rohan", 36,'m');
    driver1.addVechicle(new Vehicle("KA-01-1510","Swift", 4));

    driver2 = new User(2, "s", 36,'m');
    driver2.addVechicle(new Vehicle("KA-01-1511","Baleno", 4));

    DriverManager driverManager = new DriverManager();

    driverManager.createDriver(driver1);
    driverManager.createDriver(driver2);

    rider1 = new User(1, "Rohan", 36,'m');
    rider2 = new User(2, "p", 36,'m');
    rider3 = new User(3, "k", 36,'m');

    RiderManager riderManager = new RiderManager();
    riderManager.createRider(rider1);
    riderManager.createRider(rider2);
    riderManager.createRider(rider3);

    tripManager =
            new TripManager(
                    riderManager, driverManager, new OptimalDriverStrategy());

  }

  @Test
  void test_createTripMethod() {
    final String trip1 = tripManager.createTrip(driver1, "bengaluru", "hosur", 4);

    assertThrows(
        InvalidRideParamException.class,
        () -> {
          tripManager.createTrip(driver2, "bengaluru", "bengaluru", 4);;
        });
  }

  @Test
  void test_selectTripMethod() {
    final String trip1 = tripManager.createTrip(driver1, "bengaluru", "hosur", 4);
    final String selectedTrip = tripManager.selectTrip(rider1, "bengaluru", "hosur", 2);
    assertEquals(trip1, selectedTrip);
  }

//  @Test
//  void test_withdrawTrip() {
//
//    // Given.
//    final String trip1 = tripManager.createTrip(driver1, "bengaluru", "hosur", 4);
//
//    // When.
//    tripManager.withdrawTrip(trip1);
//    Trip trip =
//        tripManager.tripHistory(rider1).parallelStream()
//            .filter(t -> t.getId().equals(trip1))
//            .findAny()
//            .get();
//
//    // Then.
//    Assertions.assertEquals(TripStatus.WITHDRAWN, trip.getStatus());
//  }
//


  @Test
  void test_endRideAndAddAcceptNewRiderRequest() {

    // Given.
    final String trip1 = tripManager.createTrip(driver1, "bengaluru", "hosur", 4);
    final String trip2 = tripManager.createTrip(driver2, "bengaluru", "hosur", 4);

    // End the trip of rider2 and book the ride of rider3.
    User driverForRider2 = tripManager.getDriverForTrip(trip2).get();

    // When.
    assertEquals(true, tripManager.endTrip(driverForRider2));
  }
}
