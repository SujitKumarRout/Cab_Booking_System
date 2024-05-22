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
public class Ride {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne
	private CabUser user;
	@OneToOne
	private Driver driver;
	@OneToOne
	private Location source;
	@OneToOne
	private Location destination;
	
	public Ride(CabUser user, Driver driver, Location source, Location destination) {
        this.user = user;
        this.driver = driver;
        this.source = source;
        this.destination = destination;
    }
}
