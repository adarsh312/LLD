package com.company.strategy;

import com.company.model.User;
import com.company.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface DriverMatchingStrategy {

  Optional<User> findDriver(User rider, List<User> nearByDrivers, String origin, String destination);
}
