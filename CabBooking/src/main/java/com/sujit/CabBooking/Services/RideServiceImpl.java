package com.sujit.CabBooking.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sujit.CabBooking.Exception.UserNotFoundException;
import com.sujit.CabBooking.Models.CabUser;
import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;
import com.sujit.CabBooking.Models.Ride;

@Service
public class RideServiceImpl implements RideService{

    private final List<Ride> rides = new ArrayList<>();
    
    private final DriverServiceImpl driverServiceImpl;
    private final CabUserServiceImpl cabUserServiceImpl;

    @Autowired
    public RideServiceImpl(DriverServiceImpl driverServiceImpl, CabUserServiceImpl cabUserServiceImpl) {
    	this.cabUserServiceImpl = cabUserServiceImpl;
        this.driverServiceImpl = driverServiceImpl;
    }

    @Override
    public void bookRide(CabUser user, Driver driver, Location source, Location destination) {
        rides.add(new Ride(user, driver, source, destination));
    }

    @Override
    public List<Ride> getRides() {
        return rides;
    }

    @Override
    public String findRide(String username, double sourceLat, double sourceLon, double destLat, double destLon) {
        CabUser user = cabUserServiceImpl.getCabUserByName(username);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + username);
        }

        Location source = new Location(sourceLat, sourceLon);
        Location destination = new Location(destLat, destLon);

        Driver nearestDriver = driverServiceImpl.findNearestDriver(source);

        if (nearestDriver == null) {
            return "No ride found [Since all the drivers are more than 5 units away from the user]";
        } else if (!driverServiceImpl.isDriverAvailable(nearestDriver)) {
            return "No ride found [Driver " + nearestDriver.getDriverName() + " in set to not available]";
        } else {
        	bookRide(user, nearestDriver, source, destination);
            return nearestDriver.getDriverName() + " [Available]";
        }
    }
}