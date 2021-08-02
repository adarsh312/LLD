package com.company.manager;

import com.company.exception.DriverNotFoundException;
import com.company.exception.InvalidRideParamException;
import com.company.exception.TripNotFoundException;
import com.company.exception.TripStatusException;
import com.company.model.User;
import com.company.model.Vehicle;
import com.company.model.Trip;
import com.company.model.TripStatus;
import com.company.strategy.DriverMatchingStrategy;

import java.util.*;

/** TripManager class is used to manage all riders and drivers. */
public class TripManager {

  private RiderManager riderManager;
  private DriverManager driverManager;
  private DriverMatchingStrategy driverMatchingStrategy;

  /** Mapping of Driver with it's associated trips. */
  private Map<Integer, List<Trip>> trips = new HashMap<>();

  public TripManager(
      final RiderManager riderManager,
      final DriverManager driverManager,
      final DriverMatchingStrategy driverMatchingStrategy) {
    this.riderManager = riderManager;
    this.driverManager = driverManager;
    this.driverMatchingStrategy = driverMatchingStrategy;
  }

  /**
   * Method to create a trip for rider.
   *
   * @param driver Object
   * @param origin Integer.
   * @param destination Integer.
   * @param seats Integer.
   * @return Trip Id.
   */
  public String createTrip(
          final User driver, final String origin, final String destination, final int seats) {

    // Throw exception if origin is same as destination
    if (origin == destination) {
      throw new InvalidRideParamException(
              "Origin should always be different than Destination, try with valid request.");
    }

    Trip trip = new Trip(driver,
            origin,
            destination,
            seats);

    if (!trips.containsKey(driver.getId())) {
      trips.put(driver.getId(), new ArrayList<>());
    }

    trips.get(driver.getId()).add(trip);
    driver.setCurrentTrip(trip);

    return trip.getId();
  }

  public String selectTrip(
          final User rider, final String origin, final String destination, final int seats) {

    // Throw exception if origin is same as destination
    if (origin == destination) {
    throw new InvalidRideParamException(
            "Origin should always be greater than exception, try with valid request.");
    }

    // Find a driver for this ride if not found throw exception.
    List<User> drivers = driverManager.getDrivers();
    Optional<User> matchedDriver =
            driverMatchingStrategy.findDriver(rider, drivers, origin, destination);

    if (!matchedDriver.isPresent()) {
      throw new DriverNotFoundException("Driver not found, Please try after some time");
    }

    // Create a trip for rider if all's good.
    User driver = matchedDriver.get();

    Trip currentTrip = driver.getCurrentTrip();
    currentTrip.addRiders(rider);

    return currentTrip.getId();
  }

  /**
   * Method to withdraw trip using trip Id.
   *
   * @param tripId String.
   */
  public void withdrawTrip(final String tripId) {

    Optional<Trip> optionalTrip = this.getTrip(tripId);

    if (!optionalTrip.isPresent()) {
      throw new TripNotFoundException(
          "No Trip found for the given Id = " + tripId + ", please try with valid Trip Id.");
    }

    Trip trip = optionalTrip.get();

    if (trip.getStatus().equals(TripStatus.COMPLETED)) {
      throw new TripStatusException("Trip has already been completed, can't withdraw now.");
    }

    User driver = trip.getDriver();
    trip.withdrawTrip();
  }

  /**
   * Method to end the trip.
   *
   * @param driver Object.
   * @return Calculated Fare.
   */
  public Boolean endTrip(final User driver) {
    if (driver.getCurrentTrip() == null) {
      throw new TripNotFoundException("Currently rider is not riding, please try again.");
    }

    driver.getCurrentTrip().endTrip();
    driver.setCurrentTrip(null);

    return true;
  }

  /**
   * Method to get all trips done by a particular rider.
   *
   * @param rider Object.
   * @return List of Trip.
   */
  public List<Trip> tripHistory(final User rider) {
    return trips.getOrDefault(rider.getId(), new ArrayList<>());
  }

  /**
   * Helper method to get the respective driver for the given rider.
   *
   * @param tripId Integer.
   * @return Driver.
   */
  public Optional<User> getDriverForTrip(final String tripId) {
    Optional<Trip> trip =
        this.trips.values().stream()
            .flatMap(list -> list.stream())
            .filter(t -> t.getId().equals(tripId))
            .findFirst();

    return Optional.of(trip.get().getDriver());
  }

  private Optional<Trip> getTrip(final String tripId) {
    return trips.values().stream()
        .flatMap(list -> list.stream())
        .filter(t -> t.getId().equals(tripId))
        .findFirst();
  }
}
