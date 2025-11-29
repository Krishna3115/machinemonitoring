package com.tblmonitoring.tblmonitor.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tblmonitoring.tblmonitor.dto.LOAContactDTO;
import com.tblmonitoring.tblmonitor.dto.PendingPODTO;
import com.tblmonitoring.tblmonitor.dto.PurchaseOrderDTO;
import com.tblmonitoring.tblmonitor.entity.ConsigneeContact;
import com.tblmonitoring.tblmonitor.entity.CurveDetail;
import com.tblmonitoring.tblmonitor.entity.PurchaseOrder;
import com.tblmonitoring.tblmonitor.repository.ConsigneeContactRepository;
import com.tblmonitoring.tblmonitor.repository.CurveDetailRepository;
import com.tblmonitoring.tblmonitor.repository.MachineRepository;
import com.tblmonitoring.tblmonitor.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

	 @Autowired
	    private PurchaseOrderRepository poRepo;
	 
	 @Autowired
	 private MachineRepository machineRepository;
	 
	 @Autowired
	 private CurveDetailRepository curveDetailRepo;
	 
	 @Autowired
	 private ConsigneeContactRepository contactRepo;


	    @Override
	    public PurchaseOrder createPO(PurchaseOrderDTO dto) {
	        PurchaseOrder po = new PurchaseOrder();
	        po.setPoNumber(dto.getPoNumber());
	        po.setPoDate(dto.getPoDate());
	        po.setQuantity(dto.getQuantity());
	        po.setWarrantyMonths(dto.getWarrantyMonths());
	        po.setMaintenanceDays(dto.getMaintenanceDays());
	        po.setErpoa(dto.getErpoa());
	        po.setDivision(dto.getDivision());
	        po.setSection(dto.getSection());
	        po.setDispatchDate(dto.getDispatchDate());

	        return poRepo.save(po);
	    }

	    @Override
	    public List<PurchaseOrder> getAllPOs() {
	        return poRepo.findAll();
	    }
	    
	    public List<PurchaseOrder> getPendingPOs() {
	        List<PurchaseOrder> allPOs = poRepo.findAll();

	        return allPOs.stream()
	            .filter(po -> {
	                int dispatchedCount = machineRepository.countByPurchaseOrder_Id(po.getId());
	                return dispatchedCount < po.getQuantity();
	            })
	            .toList();
	    }
	    
	    @Override
	    public List<PendingPODTO> getPendingPODetails() {
	        return poRepo.findAll().stream()
	            .map(po -> {
	                int dispatchedCount = machineRepository.countByPurchaseOrder_Id(po.getId());
	                return new PendingPODTO(po.getId(), po.getPoNumber(), po.getQuantity(), dispatchedCount);
	            })
	            .filter(dto -> dto.getRemaining() > 0)
	            .collect(Collectors.toList());
	    }
	    
	    @Override
	    public void savePOWithCurveDetailsAndContacts(String poNumber, String poDate, int quantity, String dispatchDate,
	    	    int warrantyMonths, int maintenanceDays, String erpoa, double perDayFine, String division,
	    	    String section,
	    	    String contactsJson, MultipartFile file) throws IOException {

	    	    PurchaseOrder po = new PurchaseOrder();
	    	    po.setPoNumber(poNumber);
	    	    po.setPoDate(LocalDate.parse(poDate));
	    	    po.setQuantity(quantity);
	    	    po.setDispatchDate(dispatchDate);
	    	    po.setWarrantyMonths(warrantyMonths);
	    	    po.setMaintenanceDays(maintenanceDays);
	    	    po.setErpoa(erpoa);
	    	    po.setPerDayFine(perDayFine);
	    	    po.setDivision(division);
	    	    po.setSection(section);
	    	    po = poRepo.save(po);  // Save first to get ID

	    	    // Parse and save contacts
	    	    ObjectMapper mapper = new ObjectMapper();
	    	    List<Map<String, String>> contacts = mapper.readValue(contactsJson, new TypeReference<>() {});

	    	    for (Map<String, String> c : contacts) {
	    	        String name = c.get("name");
	    	        String designation = c.get("designation");
	    	        String mobile = c.get("mobile");
	    	        String division1 = c.getOrDefault("division", "");  // <-- NEW
	    	        String section1 = c.getOrDefault("section", "");    // <-- NEW

	    	        ConsigneeContact contact = new ConsigneeContact(name, designation, mobile, po, division1, section1);
	    	        contactRepo.save(contact);
	    	    }

	    	    // Then continue parsing and saving the curve details
	    	    // (Same Excel parsing logic as before)
//	    	    Workbook workbook = new XSSFWorkbook(file.getInputStream());
//	    	    Sheet sheet = workbook.getSheetAt(0);
//	    	    int rowCount = 0;
//
//	    	    for (Row row : sheet) {
//	    	        Cell firstCell = row.getCell(0);
//	    	        String firstCellValue = getCellString(firstCell);
//	    	        if (firstCellValue == null || "Curve No".equalsIgnoreCase(firstCellValue)) continue;
//
//	    	        CurveDetail detail = new CurveDetail();
//	    	        detail.setPoNumber(poNumber);
//	    	        detail.setCurveNo(getCellString(row.getCell(0)));
//	    	        detail.setBlockSection(getCellString(row.getCell(1)));
//	    	        detail.setRailSection(getCellString(row.getCell(2)));
//	    	        detail.setLhRh(getCellString(row.getCell(3)));
//	    	        detail.setPoleNo(getCellString(row.getCell(4)));
//	    	        detail.setKmFrom(getCellInteger(row.getCell(5)));
//	    	        detail.setMetFrom(getCellInteger(row.getCell(6)));
//	    	        detail.setKmTo(getCellInteger(row.getCell(7)));
//	    	        detail.setMetTo(getCellInteger(row.getCell(8)));
//	    	        detail.setLength(getCellDouble(row.getCell(9)));
//	    	        detail.setDegree(getCellDouble(row.getCell(10)));
//	    	        detail.setPwiSection(getCellString(row.getCell(11)));
//
//	    	        curveDetailRepo.save(detail);
//	    	        rowCount++;
//	    	    }
//
//	    	    workbook.close();
//
//	    	    if (rowCount != quantity) {
//	    	        throw new IllegalArgumentException("Uploaded Excel row count does not match PO quantity.");
//	    	    }
	    	}
	    
				//Helper functions
	    private String getCellString(Cell cell) {
	        if (cell == null) return null;

	        if (cell.getCellType() == CellType.STRING) {
	            return cell.getStringCellValue().trim();
	        } else if (cell.getCellType() == CellType.NUMERIC) {
	            // Optional: If you want to remove decimal part if it's a whole number
	            double value = cell.getNumericCellValue();
	            if (value == Math.floor(value)) {
	                return String.valueOf((int) value); // e.g., 377 → "377"
	            } else {
	                return String.valueOf(value); // e.g., 377.5 → "377.5"
	            }
	        } else {
	            return cell.toString().trim(); // fallback for other types
	        }
	    }

				
		private Integer getCellInteger(Cell cell) {
		    if (cell == null) return null;

		    if (cell.getCellType() == CellType.NUMERIC) {
		        return (int) cell.getNumericCellValue();
		    } else if (cell.getCellType() == CellType.STRING) {
		        try {
		            return Integer.parseInt(cell.getStringCellValue().trim());
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException("Invalid number format in Excel cell: " + cell.getStringCellValue());
		        }
		    } else {
		        throw new IllegalStateException("Unexpected cell type for Integer: " + cell.getCellType());
		    }
		}
		
		
		private Double getCellDouble(Cell cell) {
		    if (cell == null) return null;

		    try {
		        if (cell.getCellType() == CellType.NUMERIC) {
		            return cell.getNumericCellValue(); // handles decimals and negatives
		        } else if (cell.getCellType() == CellType.STRING) {
		            String val = cell.getStringCellValue().trim();
		            return Double.parseDouble(val); // handles "-2.34" too
		        }
		    } catch (Exception e) {
		        throw new IllegalArgumentException("Invalid decimal format in Excel cell: " + cell);
		    }

		    return null;
		}

				
		@Override
		public List<LOAContactDTO> getLOAContactDetails() {
		    List<PurchaseOrder> orders = poRepo.findAllWithContacts(); // should fetch contacts eagerly or with a join

		    Map<String, LOAContactDTO> grouped = new LinkedHashMap<>();

		    for (PurchaseOrder order : orders) {
		        String key = order.getPoNumber();

		        if (!grouped.containsKey(key)) {
		            LOAContactDTO dto = new LOAContactDTO();
		            dto.setPoNumber(order.getPoNumber());

		            // ✅ Get division/section from the first contact (if any)
		            if (order.getConsigneeContacts() != null && !order.getConsigneeContacts().isEmpty()) {
		                ConsigneeContact firstContact = order.getConsigneeContacts().get(0);
		                dto.setDivision(firstContact.getDivision());
		                dto.setSection(firstContact.getSection());
		            } else {
		                dto.setDivision("-");
		                dto.setSection("-");
		            }

		            dto.setConsigneeContactList(new ArrayList<>());
		            grouped.put(key, dto);
		        }

		        List<LOAContactDTO.ConsigneeContactDTO> contacts = grouped.get(key).getConsigneeContactList();

		        if (order.getConsigneeContacts() != null) {
		            for (ConsigneeContact contact : order.getConsigneeContacts()) {
		                contacts.add(new LOAContactDTO.ConsigneeContactDTO(
		                    contact.getName(),
		                    contact.getDesignation(),
		                    contact.getMobile()
		                ));
		            }
		        }
		    }

		    return new ArrayList<>(grouped.values());
		}

		
		@Override
		public PurchaseOrder getPOById(Long id) {
		    return poRepo.findById(id)
		        .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + id));
		}
		
		
		
		@Override
		public void updatePurchaseOrder(Long id, String dispatchDate, int quantity, int warrantyMonths,
		                                int maintenanceDays, String erpoa, double perDayFine, MultipartFile file) throws IOException {

		    PurchaseOrder po = poRepo.findById(id)
		            .orElseThrow(() -> new IllegalArgumentException("Purchase Order not found with ID: " + id));

		    // ✅ Update normal fields
		    if (dispatchDate != null && !dispatchDate.isEmpty()) {
		    	po.setDispatchDate(LocalDate.parse(dispatchDate).toString());

		    }

		    po.setQuantity(quantity);
		    po.setWarrantyMonths(warrantyMonths);
		    po.setMaintenanceDays(maintenanceDays);
		    po.setErpoa(erpoa);
		    po.setPerDayFine(perDayFine);

		    poRepo.save(po);

		    // ✅ If Excel file uploaded → Parse & Save Curve Details
		    if (file != null && !file.isEmpty()) {
		        Workbook workbook = new XSSFWorkbook(file.getInputStream());
		        Sheet sheet = workbook.getSheetAt(0);

		        // Remove old curve details for same PO number (to avoid duplicates)
		        //curveDetailRepo.deleteAllById(po.getPoNumber());

		        for (Row row : sheet) {
		            // Skip empty rows
		            if (row.getCell(0) == null || getCellString(row.getCell(0)).isEmpty()) continue;

		            // Skip header row by checking if first cell is text "Curve No"
		            if (getCellString(row.getCell(0)).equalsIgnoreCase("Curve No")) continue;

		            CurveDetail detail = new CurveDetail();
		            detail.setPoNumber(po.getPoNumber());
		            detail.setCurveNo(getCellString(row.getCell(0)));
		            detail.setBlockSection(getCellString(row.getCell(1)));
		            detail.setRailSection(getCellString(row.getCell(2)));
		            detail.setLhRh(getCellString(row.getCell(3)));
		            detail.setPoleNo(getCellString(row.getCell(4)));
		            detail.setKmFrom(getCellInteger(row.getCell(5)));
		            detail.setMetFrom(getCellInteger(row.getCell(6)));
		            detail.setKmTo(getCellInteger(row.getCell(7)));
		            detail.setMetTo(getCellInteger(row.getCell(8)));
		            detail.setLength(getCellDouble(row.getCell(9)));
		            detail.setDegree(getCellDouble(row.getCell(10)));
		            detail.setPwiSection(getCellString(row.getCell(11)));

		            curveDetailRepo.save(detail);
		        }

		        workbook.close();
		        System.out.println("✅ Curve details updated for PO: " + po.getPoNumber());
		    }

		    System.out.println("✅ Purchase Order updated successfully with or without Excel for ID: " + id);
		}


		@Override
		public PurchaseOrder updatePO(Long id, PurchaseOrderDTO dto, MultipartFile file) throws IOException {
		    // 1️⃣ Fetch existing PO
		    PurchaseOrder po = poRepo.findById(id)
		            .orElseThrow(() -> new IllegalArgumentException("PO not found with ID: " + id));

		    // 2️⃣ Update basic fields from DTO
		    if (dto.getDispatchDate() != null) po.setDispatchDate(dto.getDispatchDate());
		    po.setQuantity(dto.getQuantity());
		    po.setWarrantyMonths(dto.getWarrantyMonths());
		    po.setMaintenanceDays(dto.getMaintenanceDays());
		    po.setErpoa(dto.getErpoa());
		    po.setPerDayFine(dto.getPerDayFine());

		    poRepo.save(po);
		    System.out.println("✅ PO updated: " + po.getPoNumber());

		    // 3️⃣ If Excel file is uploaded, parse and save rows to curve_details table
		    if (file != null && !file.isEmpty()) {

		        // 3a: Delete existing curve details for this PO
		        List<CurveDetail> existingDetails = curveDetailRepo.findByPoNumber(po.getPoNumber());
		        if (!existingDetails.isEmpty()) {
		            curveDetailRepo.deleteByPoNumber(po.getPoNumber());
		            System.out.println("🧹 Old curve details deleted for PO: " + po.getPoNumber());
		        }

		        int savedRows = 0;
		        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
		            Sheet sheet = workbook.getSheetAt(0);

		            for (Row row : sheet) {
		                if (row.getRowNum() == 0) continue; // Skip header
		                if (row.getCell(0) == null || getCellString(row.getCell(0)).isEmpty()) continue; // Skip empty rows

		                CurveDetail detail = new CurveDetail();
		                detail.setPoNumber(po.getPoNumber());
		                detail.setCurveNo(getCellString(row.getCell(0)));
		                detail.setBlockSection(getCellString(row.getCell(1)));
		                detail.setRailSection(getCellString(row.getCell(2)));
		                detail.setLhRh(getCellString(row.getCell(3)));
		                detail.setPoleNo(getCellString(row.getCell(4)));
		                detail.setKmFrom(getCellInteger(row.getCell(5)));
		                detail.setMetFrom(getCellInteger(row.getCell(6)));
		                detail.setKmTo(getCellInteger(row.getCell(7)));
		                detail.setMetTo(getCellInteger(row.getCell(8)));
		                detail.setLength(getCellDouble(row.getCell(9)));
		                detail.setDegree(getCellDouble(row.getCell(10)));
		                detail.setPwiSection(getCellString(row.getCell(11)));

		                curveDetailRepo.save(detail);
		                savedRows++;
		            }
		        } catch (Exception e) {
		            throw new IOException("Failed to parse Excel file: " + e.getMessage(), e);
		        }

		        // Optional: Check row count vs PO quantity
		        if (savedRows != po.getQuantity()) {
		            System.out.println("⚠️ Warning: Excel row count (" + savedRows + ") != PO quantity (" + po.getQuantity() + ")");
		        }

		        System.out.println("✅ Saved " + savedRows + " curve details for PO: " + po.getPoNumber());
		    }

		    return po;
		}

		// --- Utility methods ---
		
		private void saveCurveDetailsFromExcel(String poNumber, MultipartFile file) throws IOException {
		        Workbook workbook = new XSSFWorkbook(file.getInputStream());
		        Sheet sheet = workbook.getSheetAt(0);

		        int savedRows = 0;
		        for (Row row : sheet) {
		            if (row.getRowNum() == 0) continue; // skip header
		            Cell firstCell = row.getCell(0);
		            if (firstCell == null || firstCell.getCellType() == CellType.BLANK) continue;

		            CurveDetail detail = new CurveDetail();
		            detail.setPoNumber(poNumber);
		            detail.setCurveNo(getCellString(row.getCell(0)));
		            detail.setBlockSection(getCellString(row.getCell(1)));
		            detail.setRailSection(getCellString(row.getCell(2)));
		            detail.setLhRh(getCellString(row.getCell(3)));
		            detail.setPoleNo(getCellString(row.getCell(4)));
		            detail.setKmFrom(getCellInteger(row.getCell(5)));
		            detail.setMetFrom(getCellInteger(row.getCell(6)));
		            detail.setKmTo(getCellInteger(row.getCell(7)));
		            detail.setMetTo(getCellInteger(row.getCell(8)));
		            detail.setLength(getCellDouble(row.getCell(9)));
		            detail.setDegree(getCellDouble(row.getCell(10)));
		            detail.setPwiSection(getCellString(row.getCell(11)));

		            curveDetailRepo.save(detail);
		            savedRows++;
		        }

		        workbook.close();
		        System.out.println("✅ Saved " + savedRows + " curve rows for PO: " + poNumber);
		    }

	

	
		
		
}
