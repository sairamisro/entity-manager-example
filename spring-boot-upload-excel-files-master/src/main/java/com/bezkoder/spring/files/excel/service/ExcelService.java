package com.bezkoder.spring.files.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.excel.helper.ExcelHelper;
import com.bezkoder.spring.files.excel.model.SampleInventory;
import com.bezkoder.spring.files.excel.model.Tutorial;
import com.bezkoder.spring.files.excel.repository.InventoryRepository;
import com.bezkoder.spring.files.excel.repository.TutorialRepository;

@Service
public class ExcelService {

	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
    TutorialRepository tutorialRepository;
	
	Logger logger = LoggerFactory.getLogger(ExcelService.class);

	public List<Tutorial> getTutorials() {
		logger.debug("save-method start.");
		List<Tutorial> tutorials = null;
		try {
			tutorials = tutorialRepository.getTutorials();
		} catch (Exception e) {
			logger.debug("save-method exception.");
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
		return tutorials;
	}

	public void save(MultipartFile file) {
		logger.debug("save-method start.");

		try {
			List<SampleInventory> sampleInventory = ExcelHelper.excelToInventory(file.getInputStream());
			inventoryRepository.saveAll(sampleInventory);
			logger.debug("save-method end.");

		} catch (IOException e) {
			logger.debug("save-method exception.");

			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		logger.debug("load-method start.");

		List<SampleInventory> sampleInventory = inventoryRepository.findAll();

		ByteArrayInputStream in = ExcelHelper.inventoryToExcel(sampleInventory);
		logger.debug("load-method end.");
		return in;
	}

	public Map<String, Object> getSampleInventorys(Map<String, String> request) {
		
		logger.debug("getSampleInventorys-method start.");
		
		int page = Integer.parseInt(request.get("page"));
		int size = Integer.parseInt(request.get("size"));
		String supplierName = request.get("supplierName");
		String productNameFilter = request.get("productNameFilter");
		boolean isExpiry = Boolean.parseBoolean(request.get("isExpiry"));

		Page<SampleInventory> pageTuts;
		Pageable paging = PageRequest.of(page, size);
		List<SampleInventory> sampleInventorys = new ArrayList<SampleInventory>();

		if ((supplierName != null && !supplierName.isEmpty()) && (productNameFilter != null && !productNameFilter.isEmpty()) && isExpiry) {
			logger.debug("getSampleInventorys-method filter-1.");
			pageTuts = inventoryRepository.findBySupplierAndStockAndNameNoExpire(supplierName, productNameFilter, paging);
			
		} else if ((supplierName != null && !supplierName.isEmpty()) && (productNameFilter != null && !productNameFilter.isEmpty())) {
			logger.debug("getSampleInventorys-method filter-2.");
			pageTuts = inventoryRepository.findBySupplierAndStockAndName(supplierName, productNameFilter, paging);
			
		} else if ((supplierName != null && !supplierName.isEmpty()) && (productNameFilter == null || productNameFilter.isEmpty())) {
			logger.debug("getSampleInventorys-method filter-3.");
			pageTuts = inventoryRepository.findBySupplierAndStock(supplierName, paging);
			
		} else if (isExpiry) {
			logger.debug("getSampleInventorys-method filter-4.");
			pageTuts = inventoryRepository.findByNoExpire(paging);
			
		} else
			pageTuts = inventoryRepository.findAll(paging);

		sampleInventorys = pageTuts.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("sampleInventorys", sampleInventorys);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());

		logger.debug("getSampleInventorys-method end.");
		
		return response;
	}

}
