package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.dao.AdminRepository;
import com.example.entities.Admin;

public class CustomeAdminDetailsServiceImplementataion implements UserDetailsService{

	@Autowired
	private AdminRepository adminRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin userByUserName = adminRepository.getUserByUserName(username);
		if(userByUserName == null)
		{
			throw new UsernameNotFoundException("Could not found user.");
		}
		
		CustomeAdminDetails customeAdminDetails = new CustomeAdminDetails(userByUserName);
		return customeAdminDetails;
	}

}
