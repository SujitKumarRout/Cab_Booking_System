package com.sujit.CabBooking.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sujit.CabBooking.Exception.UserNotFoundException;
import com.sujit.CabBooking.Models.CabUser;

@Service
public class CabUserServiceImpl implements CabUserService{

    private final Map<String, CabUser> cabUsers = new HashMap<>();

    @Override
    public CabUser getCabUserByName(String username) {
    	 if (username == null) {
             throw new UserNotFoundException("User not found: " + username);
         }
        return cabUsers.get(username);
    }
    
    @Override
    public CabUser addCabUser(CabUser cabUser) {
        cabUsers.put(cabUser.getUsername(), cabUser);
        return cabUser;
    }
    
    @Override
    public Map<String, CabUser> getAllCabUsers() {
        return cabUsers;
    }
}