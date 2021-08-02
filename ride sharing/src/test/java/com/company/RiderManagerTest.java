package com.company;

import com.company.exception.RiderAlreadyPresentException;
import com.company.exception.RiderNotFoundException;
import com.company.manager.RiderManager;
import com.company.model.User;
import com.company.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RiderManagerTest {

	RiderManager riderManager;

	@BeforeEach
	void setup() {
		riderManager = new RiderManager();
	}

	@Test
	void test_createRiderAndGetRider() {
		// Given.
		riderManager.createRider(new User(1, "Amar", 36,'F'));
		riderManager.createRider(new User(2, "Shubham", 22, 'M'));

		// When.
		User rider1 = riderManager.getRider(1);
		User rider2 = riderManager.getRider(2);

		// Then.
		assertEquals("Amar", rider1.getName());
		assertEquals("Shubham", rider2.getName());

		// Then.
		assertThrows(RiderNotFoundException.class, () -> {
			// When.
			riderManager.getRider(4);
		});
	}

	@Test
	void test_createRiderWithDuplicateIdMethod() {
		// Given.
		User rider2 = new User(2, "Prateek" , 36,'F');
		riderManager.createRider(new User(1, "Amar", 36,'F'));
		riderManager.createRider(new User(2, "Prateek", 36,'F'));

		// Then.
		assertThrows(RiderAlreadyPresentException.class, () -> {
			// When.
			riderManager.createRider(rider2);
		});
	}

}
