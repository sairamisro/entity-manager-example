package com.bezkoder.spring.files.excel.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bezkoder.spring.files.excel.helper.ExcelHelper;
import com.bezkoder.spring.files.excel.message.ResponseMessage;
import com.bezkoder.spring.files.excel.model.Tutorial;
import com.bezkoder.spring.files.excel.service.ExcelService;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

	Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@Autowired
	ExcelService fileService;
	
	//http://localhost:8080/api/excel/getTutorials

	@GetMapping("/getTutorials")
	public ResponseEntity<List<Tutorial>> getTutorials() {
		logger.debug("ExcelController :: getTutorials-method start.");
		try {
			List<Tutorial> list = fileService.getTutorials();
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			logger.debug("ExcelController :: getTutorials-method end.");
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("ExcelController :: getTutorials-method exception." + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

		logger.debug("ExcelController :: uploadFile-method start.");

		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				fileService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				logger.debug("ExcelController :: uploadFile-method exception." + e.getMessage());
				e.printStackTrace();
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";

		logger.debug("ExcelController :: uploadFile-method end.");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> getSampleInventorys(@RequestBody Map<String, String> request) {

		logger.debug("ExcelController :: getSampleInventorys-method start.");

		try {

			Map<String, Object> sampleInventorys = fileService.getSampleInventorys(request);

			if (sampleInventorys.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			logger.debug("ExcelController :: getSampleInventorys-method end.");

			return new ResponseEntity<>(sampleInventorys, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("ExcelController :: getSampleInventorys-method exception." + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		logger.debug("ExcelController :: getFile-method start.");

		String filename = "SampleInventory.xlsx";
		InputStreamResource file = new InputStreamResource(fileService.load());

		logger.debug("ExcelController :: getFile-method end.");

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

}
