package com.sujit.CabBooking.Services;

import java.util.Map;

import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;

public interface DriverService {
	
	 public Driver addDriver(Driver driver);

	 public Driver getDriver(String driverName);
	    
	 public Map<String, Driver> getAllDriver();
	    
	 public boolean isDriverAvailable(Driver driver);

	 public void setDriverAvailability(Driver driver, boolean isAvailable);

	 public Driver findNearestDriver(Location location);
}
