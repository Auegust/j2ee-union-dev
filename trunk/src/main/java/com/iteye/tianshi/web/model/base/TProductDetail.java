package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.iteye.tianshi.core.web.model.BaseEntity;

/**
 * 经销商销售明细表
 * 
 */
@Entity
@Table(name = "t_product_list")
public class TProductDetail extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "check_flag")
	private String checkFlag;

	@Column(name = "check_man")
	private String checkMan;

	@DateTimeFormat(iso=ISO.DATE) 
	@Column(name="create_time" ,updatable=false)
	private Date createTime;

	private String creator;

	private String remark;

	@Column(name = "sale_number")
	private Integer saleNumber;

	@Column(name = "sale_price")
	private Double salePrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sale_time")
	private Date saleTime;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "distributor_code")
	private String distributorCode;

	@Transient
	private String shopCode;

	@Transient
	private String shopName;

	@Transient
	private String productName;

	@Transient
	private String distributorName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public double getPv() {
		return pv;
	}

	public void setPv(double pv) {
		this.pv = pv;
	}

	public double getBv() {
		return bv;
	}

	public void setBv(double bv) {
		this.bv = bv;
	}

	@Transient
	private double pv;

	@Transient
	private double bv;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public TProductDetail() {
	}

	public String getCheckFlag() {
		return this.checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getCheckMan() {
		return this.checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSaleNumber() {
		return this.saleNumber;
	}

	public void setSaleNumber(Integer saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Date getSaleTime() {
		return this.saleTime;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
}