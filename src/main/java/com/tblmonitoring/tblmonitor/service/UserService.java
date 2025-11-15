package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tblmonitoring.tblmonitor.dto.LoginResponse;
import com.tblmonitoring.tblmonitor.dto.ActivationRequest;
import com.tblmonitoring.tblmonitor.dto.AdminLoginRequest;
import com.tblmonitoring.tblmonitor.dto.AdminRegisterRequest;
import com.tblmonitoring.tblmonitor.dto.CompleteProfileRequestDTO;
import com.tblmonitoring.tblmonitor.dto.RegisterRequest;
import com.tblmonitoring.tblmonitor.entity.Users;

public interface UserService {

	
	 Users registerUser(Users user);
	    //Optional<Users> findByMobile(String mobileNumber);
	    
	  //  Users registerUser(Users user);
	    Users getUserByMobile(String mobile);
	    String registerUser(RegisterRequest request);
	    String activateUser(ActivationRequest request);
	    Optional<Users> findByMobile(String mobileNumber);
	    LoginResponse loginUser(String mobileNumber, String password);
	    
	    String registerAdmin(AdminRegisterRequest request);
	    String adminLogin(String mobileNumber, String password);
	    String login(AdminLoginRequest request);
	    String completeUserProfile(Long userId, CompleteProfileRequestDTO request);
	    long countActiveTechnicians();
	 //   String LoginResponse (String mobileNumber, String password);
	    String blockUnblockTechnician(Long userId, boolean block);
	    List<Users> getAllTechnicians();
	    List<Users> getInactiveUsers();
	    List<Users> getBlockedTechnicians();
	    List<Users> getUnblockedTechnicians();

		UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException;
}
