package com.tblmonitoring.tblmonitor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>{

	Optional<PurchaseOrder> findByPoNumber(String poNumber);
	

    @Query("SELECT DISTINCT po FROM PurchaseOrder po LEFT JOIN FETCH po.consigneeContacts")
    List<PurchaseOrder> findAllWithContacts();

}

