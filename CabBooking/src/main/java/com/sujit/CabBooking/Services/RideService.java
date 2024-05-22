package com.sujit.CabBooking.Services;

import java.util.List;

import com.sujit.CabBooking.Models.CabUser;
import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;
import com.sujit.CabBooking.Models.Ride;

public interface RideService {
	
	public void bookRide(CabUser user, Driver driver, Location source, Location destination);

    public List<Ride> getRides();

    public String findRide(String username, double sourceLat, double sourceLon, double destLat, double destLon);
}