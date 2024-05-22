package com.sujit.CabBooking.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String driverName;
	private String gender;
	private int age;
	private String vehicleDetails;
	@OneToOne
	private Location currentLocation;
	private boolean available;
	
	public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
	public Driver(String driverName, String gender, int age, String vehicleDetails, Location currentLocation) {
		this.driverName = driverName;
		this.gender = gender;
		this.age = age;
		this.vehicleDetails = vehicleDetails;
		this.currentLocation = currentLocation;
	}
}

