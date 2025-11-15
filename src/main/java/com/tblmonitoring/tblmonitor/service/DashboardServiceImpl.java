package com.tblmonitoring.tblmonitor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tblmonitoring.tblmonitor.dto.PendingUserDTO;

import com.tblmonitoring.tblmonitor.dto.AdminDashboardDTO;
import com.tblmonitoring.tblmonitor.dto.SuperAdminDashboardDTO;
import com.tblmonitoring.tblmonitor.entity.Users;
import com.tblmonitoring.tblmonitor.repository.InspectionRepository;
import com.tblmonitoring.tblmonitor.repository.MachineInstallationRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.UserRepository;
import com.tblmonitoring.tblmonitor.repository.VandalismReportRepository;

@Service
public class DashboardServiceImpl implements DashboardService{

	 	@Autowired
	    private MachineRepository machineRepo;

	    @Autowired
	    private UserRepository userRepo;

	    @Autowired
	    private InspectionRepository inspectionRepo;

	    @Autowired
	    private VandalismReportRepository vandalismRepo;
	    
	    @Autowired
	    private MachineInstallationRepository installationRepo;

	    public SuperAdminDashboardDTO getSuperAdminDashboard() {
	        int totalMachines = (int) machineRepo.count();
	        long totalUsers = userRepo.countByRole("USER");
	        long totalAdmins = userRepo.countByRole("ADMIN");
	        int pendingInspections = inspectionRepo.countByMachineStatus("PENDING");
	        int totalVandalismReports = (int) vandalismRepo.count();

	        return new SuperAdminDashboardDTO(totalMachines, totalUsers, totalAdmins, pendingInspections, totalVandalismReports);
	    }

		@Override
		public SuperAdminDashboardDTO getSuperAdminDashboardData() {
			// TODO Auto-generated method stub
			long totalMachines = machineRepo.count();
		    long totalUsers = userRepo.countByRole("USER");
		    long totalAdmins = userRepo.countByRole("ADMIN");
		    long pendingInspections = inspectionRepo.countByMachineStatus("PENDING");
		    long totalVandalismReports = vandalismRepo.count();

		    return new SuperAdminDashboardDTO(
		        totalMachines,
		        totalUsers,
		        totalAdmins,
		        pendingInspections,
		        totalVandalismReports
		    );
		}

		
		@Override
		public AdminDashboardDTO getAdminDashboardData(Long adminId) {
			// TODO Auto-generated method stub
			
			Users admin = userRepo.findById(adminId)
				    .orElseThrow(() -> new RuntimeException("Admin not found"));

				if (!admin.getRole().equalsIgnoreCase("ADMIN")) {
				    throw new RuntimeException("User is not an admin");
				}
			
			long totalMachines = machineRepo.count(); // Total machines in the database
		    long totalInstallations = installationRepo.count(); // ✅ This line counts all installations
		    long submittedInspections = inspectionRepo.countByMachineStatus("SUBMITTED");
		    long pendingInspections = inspectionRepo.countByMachineStatus("PENDING");
		    long totalTechnicians = userRepo.countByRole("USER");

		    // Pending user registrations
		    List<Users> pendingUsers = userRepo.findByIsActiveFalseAndRole("USER");
		    List<PendingUserDTO> pendingUserDTOs = pendingUsers.stream()
		        .map(user -> new PendingUserDTO(
		                user.getId(),
		                user.getName(),
		                user.getMobileNumber(),
		                user.getActivationCode()
		        ))
		        .collect(Collectors.toList());

		    return new AdminDashboardDTO(
		            totalMachines,
		            totalInstallations,
		            submittedInspections,
		            pendingInspections,
		            totalTechnicians,
		            pendingUserDTOs
		    );
		}
		
		
		
}
