package com.company;

import com.company.exception.DriverAlreadyPresentException;
import com.company.exception.DriverNotFoundException;
import com.company.manager.DriverManager;
import com.company.model.User;
import com.company.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DriverManagerTest {

	DriverManager driverManager;

	@BeforeEach
	void setup() {
		driverManager = new DriverManager();
	}

	@Test
	void test_createDriverAndGetDrivers() {
		User dummyDriver = new User(1, "Rohan", 36,'m');
		User d1 = new User(1, "Rohan", 36,'m');
		d1.addVechicle(new Vehicle("KA-01-1510","Swift", 4));
		driverManager.createDriver(d1);
		User d2 = new User(2, "s", 36,'m');
		d2.addVechicle(new Vehicle("KA-01-1511","Baleno", 4));
		driverManager.createDriver(d2);
		User d3 = new User(3, "k", 36,'m');
		d3.addVechicle(new Vehicle("KA-01-1512","XUV", 5));
		driverManager.createDriver(d3);

		// Then.
		assertThrows(DriverAlreadyPresentException.class, () -> {
			// When.
			driverManager.createDriver(dummyDriver);
		});

		// Then.
		assertEquals(3, driverManager.getDrivers().size());
	}

	@Test
	void test_updateDriverAvailability() {

		User dummyDriver = new User(1, "Rohan", 36,'m');
		User d1 = new User(1, "Rohan", 36,'m');
		d1.addVechicle(new Vehicle("KA-01-1510","Swift", 4));
		driverManager.createDriver(d1);
		User d2 = new User(2, "s", 36,'m');
		d2.addVechicle(new Vehicle("KA-01-1511","Baleno", 4));
		driverManager.createDriver(d2);
		User d3 = new User(3, "k", 36,'m');
		d3.addVechicle(new Vehicle("KA-01-1512","XUV", 5));
		driverManager.createDriver(d3);

		assertEquals(3, driverManager.getDrivers().size());

		// When.
		driverManager.updateDriverAvailability(3, false);

		// Then.
		assertEquals(2, driverManager.getDrivers().size());

		// Then.
		assertThrows(DriverNotFoundException.class, () -> {
			// When.
			driverManager.updateDriverAvailability(10, false);
		});
	}
}
