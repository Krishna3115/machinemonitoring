package com.tblmonitoring.tblmonitor.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tblmonitoring.tblmonitor.dto.LoginResponse;

import com.tblmonitoring.tblmonitor.dto.ActivationRequest;
import com.tblmonitoring.tblmonitor.dto.AdminRegisterRequest;
import com.tblmonitoring.tblmonitor.dto.CompleteProfileRequestDTO;
import com.tblmonitoring.tblmonitor.dto.LoginRequest;
import com.tblmonitoring.tblmonitor.dto.RegisterRequest;
import com.tblmonitoring.tblmonitor.dto.TechnicianDTO;
import com.tblmonitoring.tblmonitor.entity.Users;
//import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private UserService userService;
	
		//Submission of User Registration Details
	 	@PostMapping("/register")
	    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest req) {
	        userService.registerUser(req);
	        return ResponseEntity.ok("Registered successfully. Await activation code from admin.");
	    }

	 	@PostMapping("/adminRegister")
	    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterRequest request) {
	        try {
	            String result = userService.registerAdmin(request);
	            return ResponseEntity.status(HttpStatus.CREATED).body(result);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	 	
	 	//Generate Activation Code For User Registration
	    @PostMapping("/activate")
	    public ResponseEntity<?> activateUser(@RequestBody ActivationRequest request) {
	        String response = userService.activateUser(request);
	        return ResponseEntity.ok(response);
	    }
    
	    @GetMapping("/inactive")
	    public ResponseEntity<List<Users>> getInactiveUsers() {
	    	  List<Users> users = userService.getInactiveUsers();

	    	    users.sort(Comparator.comparing(
	    	        Users::getName,
	    	        Comparator.nullsLast(String::compareToIgnoreCase)
	    	    ));

	    	    return ResponseEntity.ok(users);
	    }

	    
	    //Functionality for User Login
//	    @PostMapping("/login") 
//	    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
//	        try {
//	            String result = userService.loginUser(loginRequest.getMobileNumber(), loginRequest.getPassword());
//	            return ResponseEntity.ok(result);
//	        } catch (RuntimeException ex) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
//	        }
//	    }
	    
	    @PostMapping("/login") 
	    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
	        try {
	            LoginResponse result = userService.loginUser(loginRequest.getMobileNumber(), loginRequest.getPassword());
	            return ResponseEntity.ok(result);
	        } catch (RuntimeException ex) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	        }
	    }
	    
	    @PostMapping("/complete-profile/{id}")
	    public ResponseEntity<String> completeProfile(
	        @PathVariable("id") Long userId,
	        @RequestBody CompleteProfileRequestDTO request
	    ) {
	        String response = userService.completeUserProfile(userId, request);
	        return ResponseEntity.ok(response);
	    }
	    
	    @GetMapping("/count/users")
	    public ResponseEntity<Long> countActiveTechnicians() {
	        long count = userService.countActiveTechnicians();
	        return ResponseEntity.ok(count);
	    }

	    @PostMapping("/technician/{id}/block")
	    public ResponseEntity<String> blockTechnician(@PathVariable("id") Long id) {
	        String response = userService.blockUnblockTechnician(id, true);
	        return ResponseEntity.ok(response);
	    }
	    
	    @GetMapping("/blocked-technicians")
	    public List<Users> getBlockedTechnicians() {
	        return userService.getBlockedTechnicians();
	    }


	    @PostMapping("/technician/{id}/unblock")
	    public ResponseEntity<String> unblockTechnician(@PathVariable("id") Long id) {
	        String response = userService.blockUnblockTechnician(id, false);
	        return ResponseEntity.ok(response);
	    }

//	    @GetMapping("/technicians")
//	    public ResponseEntity<List<Users>> getAllTechnicians() {
//	        List<Users> technicians = userService.getAllTechnicians();
//	        return ResponseEntity.ok(technicians);
//	    }
	    
	    @GetMapping("/technicians")
	    public List<Users> getUnblockedTechnicians() {
	        return userService.getUnblockedTechnicians();
	    }

	    @GetMapping("/technicians/dropdown")
	    public ResponseEntity<List<TechnicianDTO>> getTechniciansForDropdown() {
	        List<Users> technicians = userService.getUnblockedTechnicians();

	        // Map Users entity to DTO with only id and name
	        List<TechnicianDTO> dtoList = technicians.stream()
	            .map(t -> new TechnicianDTO(t.getId(), t.getName()))
	            .toList();

	        return ResponseEntity.ok(dtoList);
	    }

	    
	    
}
