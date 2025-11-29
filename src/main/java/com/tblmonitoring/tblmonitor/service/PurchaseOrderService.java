package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.LOAContactDTO;
import com.tblmonitoring.tblmonitor.dto.PendingPODTO;
import com.tblmonitoring.tblmonitor.dto.PurchaseOrderDTO;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;

public interface PurchaseOrderService {

	 	PurchaseOrder createPO(PurchaseOrderDTO dto);
	    List<PurchaseOrder> getAllPOs();
		List<PendingPODTO> getPendingPODetails();
		void savePOWithCurveDetailsAndContacts(String poNumber, String poDate, int quantity, String dispatchDate,
	    	    int warrantyMonths, int maintenanceDays, String erpoa, double perDayFine, String division, String section,
	    	    String contactsJson, MultipartFile file) throws IOException ;
		 List<LOAContactDTO> getLOAContactDetails();
		 PurchaseOrder getPOById(Long id);

		 PurchaseOrder updatePO(Long id, PurchaseOrderDTO dto, MultipartFile file) throws IOException;
		 void updatePurchaseOrder(Long id, String dispatchDate, int quantity, int warrantyMonths,
                 int maintenanceDays, String erpoa, double perDayFine, MultipartFile file) throws IOException;

}
