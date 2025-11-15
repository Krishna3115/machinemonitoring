package com.tblmonitoring.tblmonitor.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tblmonitoring.tblmonitor.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByMobileNumber(String mobileNumber);

}
