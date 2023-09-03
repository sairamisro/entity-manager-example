package com.bezkoder.spring.files.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
public class SampleInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "batch")
	private String batch;

	@Column(name = "stock")
	private Double stock;

	@Column(name = "deal")
	private Double deal;

	@Column(name = "free")
	private Double free;

	@Column(name = "mrp")
	private Double mrp;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "exp")
	private Date exp;

	@Column(name = "company")
	private String company;

	@Column(name = "supplier")
	private String supplier;

	public SampleInventory() {

	}

	public SampleInventory(long id, String code, String name, String batch, Double stock, Double deal, Double free,
			Double mrp, Double rate, Date exp, String company, String supplier) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.batch = batch;
		this.stock = stock;
		this.deal = deal;
		this.free = free;
		this.mrp = mrp;
		this.rate = rate;
		this.exp = exp;
		this.company = company;
		this.supplier = supplier;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getDeal() {
		return deal;
	}

	public void setDeal(Double deal) {
		this.deal = deal;
	}

	public Double getFree() {
		return free;
	}

	public void setFree(Double free) {
		this.free = free;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "SampleInventory [id=" + id + ", code=" + code + ", name=" + name + ", batch=" + batch + ", stock="
				+ stock + ", deal=" + deal + ", free=" + free + ", mrp=" + mrp + ", rate=" + rate + ", exp=" + exp
				+ ", company=" + company + ", supplier=" + supplier + "]";
	}

}
