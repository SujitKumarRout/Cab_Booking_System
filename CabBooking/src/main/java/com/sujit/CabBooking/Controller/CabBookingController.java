package com.sujit.CabBooking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sujit.CabBooking.Exception.NoAvailableDriverException;
import com.sujit.CabBooking.Exception.UserNotFoundException;
import com.sujit.CabBooking.Models.CabUser;
import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;
import com.sujit.CabBooking.Services.CabUserService;
import com.sujit.CabBooking.Services.DriverService;
import com.sujit.CabBooking.Services.RideService;

@RestController
@RequestMapping("/cabbooking")
public class CabBookingController {

	    @Autowired
	    private CabUserService cabUserService;

	    @Autowired
	    private DriverService driverService;

	    @Autowired
	    private RideService rideService;

	    @PostMapping("/addUser")
	    public ResponseEntity<CabUser> addUser(@RequestBody CabUser user) {
	    	return new ResponseEntity<>(cabUserService.addCabUser(user), HttpStatus.CREATED);
	    }
	    
	    @PostMapping("/addDriver")
	    public ResponseEntity<Driver> addDriver(@RequestBody Driver driver) {
	        return new ResponseEntity<>(driverService.addDriver(driver), HttpStatus.CREATED);
	    }
	    
	    @GetMapping("/findRide")
	    public List<Driver> findRide(@RequestParam String userName,
	                                 @RequestParam double sourceLat,
	                                 @RequestParam double sourceLng,
	                                 @RequestParam double destLat,
	                                 @RequestParam double destLng) {
	    CabUser user = cabUserService.getCabUserByName(userName);
	        if (user == null) {
	        	throw new UserNotFoundException("User not found");
	        }
	        Location source = new Location(sourceLat, sourceLng);
	        Location destination = new Location(destLat, destLng);
	        Driver nearestDriver = driverService.findNearestDriver(source);

	        if (nearestDriver != null) {
	            rideService.bookRide(user, nearestDriver, source, destination);
	            return List.of(nearestDriver);
	        }
	        throw new NoAvailableDriverException("No available drivers within 5 units of distance");
	    }	
	    
	    @PostMapping("/chooseRide")
	    public void chooseRide(@RequestParam String userName, @RequestParam String driverName) {
	        CabUser user = cabUserService.getCabUserByName(userName);
	        Driver driver = driverService.getDriver(driverName);

	        if (user != null && driver != null) {
	        	Location currentLocation = driver.getCurrentLocation();
	        	Location destination = new Location(20, 20); 
	            rideService.bookRide(user, driver, currentLocation, destination);
	            System.out.println("Ride booked successfully for user " + userName + " with driver " + driverName);
	        }  else {
	            throw new UserNotFoundException("Ride booking failed. User or Driver not found.");
	        }
	}
}