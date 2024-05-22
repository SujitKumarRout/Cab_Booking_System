package com.sujit.CabBooking.Services;

import java.util.Map;

import com.sujit.CabBooking.Models.CabUser;

public interface CabUserService {
	
	public CabUser getCabUserByName(String username);

    public CabUser addCabUser(CabUser cabUser);

    public Map<String, CabUser> getAllCabUsers();
}
