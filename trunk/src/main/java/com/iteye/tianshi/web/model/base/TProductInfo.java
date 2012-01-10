package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.util.Set;


/**
 * 产品信息表
 * 
 */
@Entity
@Table(name="t_product_info")
public class TProductInfo extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="product_bv")
	private Integer productBv;

	@Column(name="product_code")
	private String productCode;

	@Column(name="product_name")
	private String productName;

	@Column(name="product_price")
	private Integer productPrice;

	@Column(name="product_pv")
	private Integer productPv;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="status")
	private String status;
	
	@OneToMany(mappedBy="TProductInfo")
	private Set<TProductDetail> TProductLists;

    public TProductInfo() {
    }

	public Integer getProductBv() {
		return this.productBv;
	}

	public void setProductBv(Integer productBv) {
		this.productBv = productBv;
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

	public Integer getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductPv() {
		return this.productPv;
	}

	public void setProductPv(Integer productPv) {
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
	
	public Set<TProductDetail> getTProductLists() {
		return this.TProductLists;
	}

	public void setTProductLists(Set<TProductDetail> TProductLists) {
		this.TProductLists = TProductLists;
	}
	
}