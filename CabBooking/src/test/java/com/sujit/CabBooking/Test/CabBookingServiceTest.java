package com.sujit.CabBooking.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.sujit.CabBooking.Models.CabUser;
import com.sujit.CabBooking.Models.Driver;
import com.sujit.CabBooking.Models.Location;
import com.sujit.CabBooking.Services.CabUserServiceImpl;
import com.sujit.CabBooking.Services.DriverServiceImpl;
import com.sujit.CabBooking.Services.RideServiceImpl;
	
@SpringBootTest
public class CabBookingServiceTest {

    @Mock
    private CabUserServiceImpl cabUserServiceImpl;
    
    @Mock
    private DriverServiceImpl driverServiceImpl;
    
    private RideServiceImpl rideServiceImpl;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rideServiceImpl = new RideServiceImpl(driverServiceImpl, cabUserServiceImpl);
    }

    @Test
    public void onboardUsers() {
        CabUser abhishek = new CabUser("Abhishek", "M", 23);
        CabUser rahul = new CabUser("Rahul", "M", 29);
        CabUser nandini = new CabUser("Nandini", "F", 22);

        Map<String, CabUser> users = new HashMap<>();
        users.put(abhishek.getUsername(), abhishek);
        users.put(rahul.getUsername(), rahul);
        users.put(nandini.getUsername(), nandini);

        when(cabUserServiceImpl.getAllCabUsers()).thenReturn(users);
        
        cabUserServiceImpl.addCabUser(abhishek);
        cabUserServiceImpl.addCabUser(rahul);
        cabUserServiceImpl.addCabUser(nandini);
        
        assertEquals(3, cabUserServiceImpl.getAllCabUsers().size());
    }
    
    @Test
    public void onboardDrivers() {
        Driver driver1 = new Driver("Driver1", "M", 22, "Swift, KA-01-12345", new Location(10, 1));
        Driver driver2 = new Driver("Driver2", "M", 29, "Swift, KA-01-12345", new Location(11, 10));
        Driver driver3 = new Driver("Driver3", "M", 24, "Swift, KA-01-12345", new Location(5, 3));

        Map<String, Driver> drivers = new HashMap<>();
        drivers.put(driver1.getDriverName(), driver1);
        drivers.put(driver2.getDriverName(), driver2);
        drivers.put(driver3.getDriverName(), driver3);
        
        when(driverServiceImpl.getAllDriver()).thenReturn(drivers);
        
        driverServiceImpl.addDriver(driver1);
        driverServiceImpl.addDriver(driver2);
        driverServiceImpl.addDriver(driver3);

        assertEquals(3, driverServiceImpl.getAllDriver().size());
    }
    
    @Test
    public void testFindRide() {
        CabUser abhishek = new CabUser("Abhishek", "M", 23);
        CabUser rahul = new CabUser("Rahul", "M", 29);
        CabUser nandini = new CabUser("Nandini", "F", 22);
        
        Driver driver1 = new Driver("Driver1", "M", 22, "Swift, KA-01-12345", new Location(10, 1));
        Driver driver2 = new Driver("Driver2", "M", 29, "Swift, KA-01-12345", new Location(11, 10));
        Driver driver3 = new Driver("Driver3", "M", 24, "Swift, KA-01-12345", new Location(5, 3));
        
        Map<String, CabUser> users = new HashMap<>();
        users.put(abhishek.getUsername(), abhishek);
        users.put(rahul.getUsername(), rahul);
        users.put(nandini.getUsername(), nandini);

        Map<String, Driver> drivers = new HashMap<>();
        drivers.put(driver1.getDriverName(), driver1);
        drivers.put(driver2.getDriverName(), driver2);
        drivers.put(driver3.getDriverName(), driver3);

        when(cabUserServiceImpl.getCabUserByName("Abhishek")).thenReturn(abhishek);
        when(cabUserServiceImpl.getCabUserByName("Rahul")).thenReturn(rahul);
        when(cabUserServiceImpl.getCabUserByName("Nandini")).thenReturn(nandini);

        when(driverServiceImpl.getAllDriver()).thenReturn(drivers);
        when(driverServiceImpl.findNearestDriver(any(Location.class))).thenAnswer(invocation -> {
            Location loc = invocation.getArgument(0);
            if (loc.getLatitude() == 0 && loc.getLongitude() == 0) {
                return null;
            } else if (loc.getLatitude() == 10 && loc.getLongitude() == 0) {
                return driver1; 
            } else if (loc.getLatitude() == 15 && loc.getLongitude() == 6) {
                return driver1;
            }
            return null;
        });
        
        when(driverServiceImpl.isDriverAvailable(driver1)).thenReturn(true);

        assertEquals("No ride found [Since all the drivers are more than 5 units away from the user]", rideServiceImpl.findRide("Abhishek", 0, 0, 20, 1));

        assertEquals("Driver1 [Available]", rideServiceImpl.findRide("Rahul", 10, 0, 15, 3));

        driver1.setAvailable(false);
        when(driverServiceImpl.isDriverAvailable(driver1)).thenReturn(false); 

        assertEquals("No ride found [Driver Driver1 in set to not available]", rideServiceImpl.findRide("Nandini", 15, 6, 20, 4));
    }

}