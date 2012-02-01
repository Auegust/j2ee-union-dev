package com.iteye.tianshi.web.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 产品信息表
 * 
 */
@Entity
@Table(name="t_product_info")
public class TProductInfo extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_price")
	private Double productPrice;

	@Transient
	private String productPrice_Name;

	@Column(name="product_pv")
	private Double productPv;
	@Transient
	private String productPv_Name;
	

	@Column(name="product_bv")
	private Double productBv;
	@Transient
	private String productBv_Name;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="status")
	private String status;
	
	@Transient
	private String status_Name;
    public TProductInfo() {
    }

	public String getProductPrice_Name() {
		return productPrice_Name;
	}

	public void setProductPrice_Name(String productPrice_Name) {
		this.productPrice_Name = productPrice_Name;
	}

	public String getProductPv_Name() {
		return productPv_Name;
	}

	public void setProductPv_Name(String productPv_Name) {
		this.productPv_Name = productPv_Name;
	}

	public String getProductBv_Name() {
		return productBv_Name;
	}

	public void setProductBv_Name(String productBv_Name) {
		this.productBv_Name = productBv_Name;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductBv() {
		return productBv;
	}

	public void setProductBv(Double productBv) {
		this.productBv = productBv;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getProductPv() {
		return productPv;
	}

	public void setProductPv(Double productPv) {
		this.productPv = productPv;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus_Name(String status_Name) {
		this.status_Name = status_Name;
	}

	public String getStatus_Name() {
		return status_Name;
	}
	
}