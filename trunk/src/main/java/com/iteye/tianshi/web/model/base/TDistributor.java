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
	private String sponsorId;
	
	@Transient
	private String sponsorName;

	private String telephone;

	@Column(name="rank_id")
	private Long  rankId;

	@Column(name="shop_id")
	private Long shopId;

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

	public String getSponsorId() {
		return this.sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getSponsorName() {
		return sponsorName;
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
	
}