package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tblmonitoring.tblmonitor.dto.ActivationRequest;
import com.tblmonitoring.tblmonitor.dto.AdminLoginRequest;
import com.tblmonitoring.tblmonitor.dto.AdminRegisterRequest;
import com.tblmonitoring.tblmonitor.dto.CompleteProfileRequestDTO;
import com.tblmonitoring.tblmonitor.dto.LoginResponse;
import com.tblmonitoring.tblmonitor.dto.RegisterRequest;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	 	@Autowired
	    private UserRepository userRepository;
	 	
	 	@Autowired
	    private PasswordEncoder passwordEncoder;

	 	@Override
	 	public String registerUser(RegisterRequest request) {
	 	    // Check if mobile number already exists
	 	    if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
	 	        throw new RuntimeException("Mobile number already registered");
	 	    }

	 	    // Generate activation code
	 	    String activationCode = String.format("%06d", new Random().nextInt(999999));
	 	    // Encode password
	 	    String encodedPassword = passwordEncoder.encode(request.getPassword());

	 	    // Create user entity
	 	    Users user = new Users();
	 	    user.setName(request.getName());
	 	    user.setMobileNumber(request.getMobileNumber());
	 	    user.setEmail(request.getEmail());
	 	    user.setCity(request.getCity());
	 	    user.setPassword(encodedPassword);
	 	    user.setRole("USER");
	 	    user.setIsActive(false); // Not active yet
	 	    user.setActivationCode(activationCode);
	 	    user.setProfileComplete(false);
	 	    user.setAddress(null);
	 	    user.setProfilePhotoUrl(null);
	 	    user.setIdProofUrl(null);
	 	    user.setEmergencyContactNumber(null);
	 	    user.setDesignation(request.getDesignation()); // ✅ Save designation

	 	    // Save user
	 	    userRepository.save(user);

	 	    return "Technician registered successfully. Please wait for activation from admin.";
	 	}



	    @Override
	    public String activateUser(ActivationRequest request) {
	        Users user = userRepository.findByMobileNumber(request.getMobileNumber())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (user.getActivationCode().equals(request.getCode())) {
	            user.setIsActive(true);
	            userRepository.save(user);
	            return "User activated successfully";
	        } else {
	            throw new RuntimeException("Invalid activation code");
	        }
	    }

		@Override
		public Users registerUser(Users user) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Users getUserByMobile(String mobile) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<Users> findByMobile(String mobileNumber) {
			// TODO Auto-generated method stub
			return Optional.empty();
		}
		
//		@Override
//		public String loginUser(String mobileNumber, String password) {
//		    Users user = userRepository.findByMobileNumber(mobileNumber)
//		        .orElseThrow(() -> new RuntimeException("User not found"));
//
//		    if (!user.isActive()) {
//		        throw new RuntimeException("User is not activated yet.");
//		    }
//
//		    if (!passwordEncoder.matches(password, user.getPassword())) {
//		        throw new RuntimeException("Invalid password");
//		    }
//
//		    return "User Login successful";
//		}
		
		
		// Method to register Admin (Super Admin will call this method)
	    public String registerAdmin(AdminRegisterRequest request) {
	        // Check if admin already exists with the same mobile number
	    	if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
	            throw new RuntimeException("Mobile number already registered");
	        }

	        // Encode the password using BCrypt
	        String encodedPassword = passwordEncoder.encode(request.getPassword());

	        // Create a new Admin user
	        Users admin = new Users();
	        admin.setName(request.getName());
	        admin.setMobileNumber(request.getMobileNumber());
	        admin.setEmail(request.getEmail());
	        admin.setCity(request.getCity());
	        admin.setPassword(encodedPassword);
	        admin.setRole("ADMIN");
	        admin.setIsActive(true); // Admins are active immediately
	        admin.setActivationCode(null);
	        admin.setProfileComplete(true); // Admin doesn't need to complete profile
	        admin.setAddress(null);
	        admin.setProfilePhotoUrl(null);
	        admin.setIdProofUrl(null);
	        admin.setEmergencyContactNumber(null);

	        userRepository.save(admin);
	        return "Admin registered successfully.";
	    }
	    
	    
//	    @Override
//		public String adminLogin(String mobileNumber, String password) {
//		    Users admin = userRepository.findByMobileNumber(mobileNumber)
//		        .orElseThrow(() -> new RuntimeException("User not found"));
//
//		    if (!admin.isActive()) {
//		        throw new RuntimeException("User is not activated yet.");
//		    }
//
//		    if (!passwordEncoder.matches(password, admin.getPassword())) {
//		        throw new RuntimeException("Invalid password");
//		    }
//
//		    return "Admin Login successfull";
//		}

//		@Override
//		public String login(AdminLoginRequest request) {
//			// TODO Auto-generated method stub
//			
//			Users admin = userRepository.findByMobileNumber(request.getMobileNumber())
//           .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//	        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
//	            throw new RuntimeException("Invalid password");
//	        }
//
//	        if (!admin.isActive()) {
//	            throw new RuntimeException("Admin account is not active");
//	        }
//	
//	        return "Admin Login successful";
//	        
//		}

		
		@Override
		public LoginResponse loginUser(String mobileNumber, String password) {
		    Users user = userRepository.findByMobileNumber(mobileNumber)
		        .orElseThrow(() -> new RuntimeException("User not found"));

		    if (!user.isActive()) {
		        throw new RuntimeException("User is not activated yet.");
		    }
		    
//		    if (!user.isProfileComplete()) {
//		        throw new RuntimeException("Please complete your profile before proceeding.");
//		    }

		    if (!passwordEncoder.matches(password, user.getPassword())) {
		        throw new RuntimeException("Invalid password");
		    }

		    String dashboardPath;
		    switch (user.getRole().toUpperCase()) {
		        case "SUPER_ADMIN":
		            dashboardPath = "/superadmin/dashboard";
		            break;
		        case "ADMIN":
		            dashboardPath = "/admin/dashboard";
		            break;
		        case "USER":
		            dashboardPath = "/user/dashboard";
		            break;
		        default:
		            throw new RuntimeException("Invalid role: " + user.getRole());
		    }

		    return new LoginResponse(
		        "success",
		        "Login successful",
		        user.getId(),
		        user.getName(),
		        user.getRole(),   
		        dashboardPath,
		        user.isProfileComplete()
		    );
		}

		@Override
		public String adminLogin(String mobileNumber, String password) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String login(AdminLoginRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String completeUserProfile(Long userId, CompleteProfileRequestDTO request) {
		    Users user = userRepository.findById(userId)
		        .orElseThrow(() -> new RuntimeException("User not found"));

		    user.setAddress(request.getAddress());
		    user.setProfilePhotoUrl(request.getProfilePhotoUrl());
		    user.setIdProofUrl(request.getIdProofUrl());
		    user.setEmergencyContactNumber(request.getEmergencyContactNumber());
		    user.setProfileComplete(true);

		    userRepository.save(user);
		    return "Profile completed successfully.";
		}
		
		@Override
		public long countActiveTechnicians() {
		    return userRepository.countActiveAndUnblockedUsers("USER");
		}

//		 @Transactional
//		    public String blockUnblockTechnician(Long userId, boolean block) {
//		        int updated = userRepository.updateBlockedStatus(userId, block);
//		        if (updated == 1) {
//		            return block ? "Technician blocked successfully." : "Technician unblocked successfully.";
//		        }
//		        throw new RuntimeException("Technician not found.");
//		    }
		 
		 @Override
		 public String blockUnblockTechnician(Long userId, boolean block) {
		 Users user = userRepository.findById(userId)
			        .orElseThrow(() -> new RuntimeException("Technician not found"));

			    user.setBlocked(block); // ✅ This sets the is_blocked column in DB
			    
			    if (!block) {
			        user.setIsActive(true); // Optional: reactivate if unblocking
			    }

			    userRepository.save(user);

			 return block ? "Technician blocked successfully." : "Technician unblocked successfully.";
		 }
		 
		 @Override
		 public List<Users> getAllTechnicians() {
		     return userRepository.findByRoleAndIsActive("USER", true);
		 }
		 
		 @Override
		 public List<Users> getInactiveUsers() {
		     return userRepository.findByIsActiveAndRole(false, "USER");
		 }

		 @Override
		 public List<Users> getBlockedTechnicians() {
		     return userRepository.findByRoleAndIsBlockedTrue("USER");
		 }
		 
		 @Override
		 public List<Users> getUnblockedTechnicians() {
		     return userRepository.findByRoleAndIsActiveTrueAndIsBlockedFalse("USER");
		 }

		 @Override
		 public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
			return null;
		 }

}