package com.bezkoder.spring.files.excel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bezkoder.spring.files.excel.model.SampleInventory;

public interface InventoryRepository extends JpaRepository<SampleInventory, Long> {
	
	/*
	 
	 2.Given a supplier id or name, list all the products that the supplier has with stock.
	 5.Make the above api’s pageable.
	 */
	@Query("SELECT a FROM SampleInventory a WHERE supplier = ?1 AND stock >= 1 ")
	Page<SampleInventory> findBySupplierAndStock(String supplier, Pageable pageable);
	
	/*
	 
	 2.Given a supplier id or name, list all the products that the supplier has with stock.
	 3.Accept a search param for the above filter based on the product name.
	 5.Make the above api’s pageable.
	 */
	@Query("SELECT a FROM SampleInventory a WHERE supplier = ?1 AND name = ?2 AND stock >= 1")
	Page<SampleInventory> findBySupplierAndStockAndName(String supplier, String name, Pageable pageable);
 
	/*
	 
	 2.Given a supplier id or name, list all the products that the supplier has with stock.
	 3.Accept a search param for the above filter based on the product name.
	 4.Accept a param for the above api, or create a new endpoint to only list out products that didn’t yet expire for that supplier or list of suppliers.
	 5.Make the above api’s pageable.
	 */
	@Query("SELECT a FROM SampleInventory a WHERE supplier = ?1 AND name = ?2 AND stock >= 1 AND DATE(exp) >= DATE(NOW())")
	Page<SampleInventory> findBySupplierAndStockAndNameNoExpire(String supplier, String name, Pageable pageable);

	/*
	 
	 4.Accept a param for the above api, or create a new endpoint to only list out products that didn’t yet expire for that supplier or list of suppliers.
	 5.Make the above api’s pageable.
	 */
	@Query("SELECT a FROM SampleInventory a WHERE DATE(exp) >= DATE(NOW())")
	Page<SampleInventory> findByNoExpire(Pageable pageable);

 
}
