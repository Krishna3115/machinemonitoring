package com.tblmonitoring.tblmonitor.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tblmonitoring.tblmonitor.dto.LOAContactDTO;
import com.tblmonitoring.tblmonitor.dto.PendingPODTO;
import com.tblmonitoring.tblmonitor.dto.PurchaseOrderDTO;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;
import com.tblmonitoring.tblmonitor.service.PurchaseOrderService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/po-orders")
public class PurchaseOrderControl {


    @Autowired
    private PurchaseOrderService poService;

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPO(@RequestBody PurchaseOrderDTO dto) {
        return ResponseEntity.ok(poService.createPO(dto));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PendingPODTO>> getPendingPODetails() {
        return ResponseEntity.ok(poService.getPendingPODetails());
    }
    
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPOAndCurveDetails(
        @RequestParam("poNumber") String poNumber,
        @RequestParam("poDate") String poDate,
        @RequestParam("quantity") int quantity,
        @RequestParam(value = "dispatchDate") String dispatchDate,
        @RequestParam("warrantyMonths") int warrantyMonths,
        @RequestParam("maintenanceDays") int maintenanceDays,
        @RequestParam("erpoa") String erpoa,
        @RequestParam("perDayFine") double perDayFine,
        @RequestParam("division") String division,
        @RequestParam("section") String section,
        @RequestParam("contacts") String contactsJson,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            poService.savePOWithCurveDetailsAndContacts(poNumber, poDate, quantity, dispatchDate, warrantyMonths, maintenanceDays, erpoa, perDayFine, division, section, contactsJson, file);
            return ResponseEntity.ok("PO and curve details uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
    
    
    
    @GetMapping("/contact-details")
    public ResponseEntity<List<LOAContactDTO>> getContactDetailsByLOA() {
        List<LOAContactDTO> result = poService.getLOAContactDetails();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(poService.getAllPOs());
    }

    // ✅ 6. NEW → Get single Purchase Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(poService.getPOById(id));
    }

    // ✅ 7. NEW → Update existing Purchase Order
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePurchaseOrder(
            @PathVariable("id") Long id,
            @RequestParam(value = "finalDispatchDate", required = false) String finalDispatchDate,
            @RequestParam("quantity") int quantity,
            @RequestParam("warrantyMonths") int warrantyMonths,
            @RequestParam("maintenanceDays") int maintenanceDays,
            @RequestParam("erpoa") String erpoa,
            @RequestParam("perDayFine") double perDayFine,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            PurchaseOrderDTO dto = new PurchaseOrderDTO();

            // Define the formatter once
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Correct: use finalDispatchDate, not dispatchDate
            if (finalDispatchDate != null && !finalDispatchDate.isEmpty()) {
                LocalDate date = LocalDate.parse(finalDispatchDate, formatter);
                dto.setDispatchDate(date.format(formatter));  // String field in DTO
            }

            dto.setQuantity(quantity);
            dto.setWarrantyMonths(warrantyMonths);
            dto.setMaintenanceDays(maintenanceDays);
            dto.setErpoa(erpoa);
            dto.setPerDayFine(perDayFine);

            poService.updatePO(id, dto, file);
            return ResponseEntity.ok("PO updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update PO: " + e.getMessage());
        }
    }

}
    

