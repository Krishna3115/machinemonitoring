package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tblmonitoring.tblmonitor.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
    Optional<Users> findByMobileNumber(String mobileNumber);
    long countByRole(String role);
    
    
    List<Users> findByIsActiveFalseAndRole(String role);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = :role AND u.isActive = true")
    long countActiveUsersByRole(@Param("role") String role);

    // Native SQL fallback query (MySQL example)
//    @Query(value = "SELECT COUNT(*) FROM users WHERE role = :role AND is_active = 1 AND is_blocked = 0", nativeQuery = true)
//    long countActiveUsersByRoleNative(@Param("role") String role);
    
    @Query("SELECT COUNT(u) FROM Users u WHERE u.role = :role AND u.isActive = true AND u.isBlocked = false")
    long countActiveAndUnblockedUsers(@Param("role") String role);

    @Modifying
    @Query("UPDATE Users u SET u.isBlocked = :blocked WHERE u.id = :userId")
    int updateBlockedStatus(@Param("userId") Long userId, @Param("blocked") boolean blocked);


    // Optional: fetch active users for debugging
    List<Users> findByRoleAndIsActive(String role, boolean isActive);
    
    List<Users> findByIsActiveAndRole(boolean isActive, String role);

    List<Users> findByRoleAndIsBlockedTrue(String role);
    
    List<Users> findByRoleAndIsActiveTrueAndIsBlockedFalse(String role);



}
