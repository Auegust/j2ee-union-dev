package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 经销商信息表
 * 
 */
@Entity
@Table(name="t_distributor")
public class TDistributor extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	@DateTimeFormat(iso=ISO.DATE) 
	@Column(name="create_time" ,updatable=false)
	private Date createTime;

	@Column(name="distributor_code")
	private String distributorCode;

	@Column(name="distributor_name")
	private String distributorName;

	private String remark;

	@Column(name="sponsor_id")
	private Long sponsorId;
	
	@Transient
	private String sponsor_Name;
	
	@Column(name="sponsor_code")
	private String sponsorCode;

	private String telephone;

	@Column(name="rank_id")
	private Long  rankId;
	
	@Transient
	private String rankId_Name;

	@Column(name="shop_id")
	private Long shopId;
	
	@Column(name="floors")
	private Long floors;
	
	@Transient
	private String shop_Name;

    public String getShop_Name() {
		return shop_Name;
	}
	public void setShop_Name(String shop_Name) {
		this.shop_Name = shop_Name;
	}
	public TDistributor() {
    }
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDistributorCode() {
		return this.distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getDistributorName() {
		return this.distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSponsor_Name() {
		return sponsor_Name;
	}
	public void setSponsor_Name(String sponsor_Name) {
		this.sponsor_Name = sponsor_Name;
	}
	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}
	public Long getRankId() {
		return rankId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getShopId() {
		return shopId;
	}
	
	public String getRankId_Name() {
		return rankId_Name;
	}
	public void setRankId_Name(String rankId_Name) {
		this.rankId_Name = rankId_Name;
	}
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	public String getSponsorCode() {
		return sponsorCode;
	}
	
	public Long getFloors() {
		return floors;
	}
	public void setFloors(Long floors) {
		this.floors = floors;
	}
}