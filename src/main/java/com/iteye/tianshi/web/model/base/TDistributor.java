package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.util.Date;
import java.util.Set;


/**
 * 经销商信息表
 * 
 */
@Entity
@Table(name="t_distributor")
public class TDistributor extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="distributor_code")
	private String distributorCode;

	@Column(name="distributor_name")
	private String distributorName;

	private String remark;

	@Column(name="sponsor_id")
	private Long sponsorId;

	private String telephone;

	//bi-directional many-to-one association to TDistributorRank
    @ManyToOne
	@JoinColumn(name="rank_id")
	private TDistributorRank TDistributorRank;

	//bi-directional many-to-one association to TShopInfo
    @ManyToOne
	@JoinColumn(name="shop_id")
	private TShopInfo TShopInfo;

	//bi-directional many-to-one association to TDistributorBoun
	@OneToMany(mappedBy="TDistributor")
	private Set<TDistributorBoun> TDistributorBouns;

	//bi-directional many-to-one association to TDistributorGrade
	@OneToMany(mappedBy="TDistributor")
	private Set<TDistributorGrade> TDistributorGrades;

	//bi-directional many-to-one association to TProductList
	@OneToMany(mappedBy="TDistributor")
	private Set<TProductDetail> TProductLists;

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
		return this.sponsorId;
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

	public TDistributorRank getTDistributorRank() {
		return this.TDistributorRank;
	}

	public void setTDistributorRank(TDistributorRank TDistributorRank) {
		this.TDistributorRank = TDistributorRank;
	}
	
	public TShopInfo getTShopInfo() {
		return this.TShopInfo;
	}

	public void setTShopInfo(TShopInfo TShopInfo) {
		this.TShopInfo = TShopInfo;
	}
	
	public Set<TDistributorBoun> getTDistributorBouns() {
		return this.TDistributorBouns;
	}

	public void setTDistributorBouns(Set<TDistributorBoun> TDistributorBouns) {
		this.TDistributorBouns = TDistributorBouns;
	}
	
	public Set<TDistributorGrade> getTDistributorGrades() {
		return this.TDistributorGrades;
	}

	public void setTDistributorGrades(Set<TDistributorGrade> TDistributorGrades) {
		this.TDistributorGrades = TDistributorGrades;
	}
	
	public Set<TProductDetail> getTProductLists() {
		return this.TProductLists;
	}

	public void setTProductLists(Set<TProductDetail> TProductLists) {
		this.TProductLists = TProductLists;
	}
	
}