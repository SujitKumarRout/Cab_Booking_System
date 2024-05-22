package com.sujit.CabBooking.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sujit.CabBooking.Exception.DriverNotFoundException;
import com.sujit.CabBooking.Exception.NoAvailableDriverException;
import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;


@Service
public class DriverServiceImpl implements DriverService{

	 private final Map<String, Driver> drivers = new HashMap<>();
	 private final Map<Driver, Boolean> driverAvailability = new HashMap<>();
	 	
	 	@Override
	    public Driver addDriver(Driver driver) {
	    	driverAvailability.put(driver, true);
	    	drivers.put(driver.getDriverName(), driver);
	    	return driver;
	    }
	 	
	 	@Override
	    public Driver getDriver(String driverName) {
	 		if (driverName == null) {
	            throw new DriverNotFoundException("Driver not found: " + driverName);
	        }
	        return drivers.get(driverName);
	    }
	    
	 	@Override
	    public Map<String, Driver> getAllDriver() {
	        return drivers;
	    }
	    
	 	@Override
	    public boolean isDriverAvailable(Driver driver) {
	        return driverAvailability.getOrDefault(driver, false);
	    }
	 	
	 	@Override
	    public void setDriverAvailability(Driver driver, boolean isAvailable) {
	        driverAvailability.put(driver, isAvailable);
	    }

	 	 @Override
	     public Driver findNearestDriver(Location location) {
	         Driver nearestDriver = null;
	         double nearestDistance = Double.MAX_VALUE;

	         for (Driver driver : drivers.values()) {
	             if (isDriverAvailable(driver)) {
	                 Location driverLocation = driver.getCurrentLocation();
	                 if (driverLocation == null) {
	                     continue;
	                 }
	                 double distance = calculateDistance(location, driverLocation);
	                 if (distance <= 5 && distance < nearestDistance) {
	                     nearestDriver = driver;
	                     nearestDistance = distance;
	                 }
	             }
	         }
	         if (nearestDriver == null) {
	             throw new NoAvailableDriverException("No available drivers within 5 units of distance");
	         }
	         return nearestDriver;
	     }

	     private double calculateDistance(Location loc1, Location loc2) {
	         if (loc1 == null || loc2 == null) {
	             throw new IllegalArgumentException("Locations must not be null");
	         }
	         return Math.sqrt(Math.pow(loc1.getLatitude() - loc2.getLatitude(), 2) +
	                          Math.pow(loc1.getLongitude() - loc2.getLongitude(), 2));
	     }
}