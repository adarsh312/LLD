package com.company.strategy;

import com.company.model.User;
import com.company.model.Vehicle;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class OptimalDriverStrategy implements DriverMatchingStrategy {

  @Override
  public Optional<User> findDriver(
          @NonNull final User rider,
          @NonNull final List<User> nearByDrivers,
          final String origin,
          final String destination) {
    return nearByDrivers.stream()
            .filter(t -> (t.getCurrentTrip().getOrigin().equals(origin) && t.getCurrentTrip().getDestination().equals(destination)))
            .findFirst();
  }
}
