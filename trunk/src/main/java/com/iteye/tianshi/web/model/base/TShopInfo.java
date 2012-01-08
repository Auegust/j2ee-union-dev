package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.util.Date;
import java.util.Set;


/**
 * 专卖店信息表
 * 
 */
@Entity
@Table(name="t_shop_info")
public class TShopInfo extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String creator;

	private String remark;

	@Column(name="shop_addr")
	private String shopAddr;

	@Column(name="shop_city")
	private String shopCity;

	@Column(name="shop_code")
	private String shopCode;

	@Column(name="shop_country")
	private String shopCountry;

	@Column(name="shop_name")
	private String shopName;

	@Column(name="shop_owner")
	private String shopOwner;

	@OneToMany(mappedBy="TShopInfo")
	private Set<TDistributor> TDistributors;

    public TShopInfo() {
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

	public String getShopAddr() {
		return this.shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public String getShopCity() {
		return this.shopCity;
	}

	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopCountry() {
		return this.shopCountry;
	}

	public void setShopCountry(String shopCountry) {
		this.shopCountry = shopCountry;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopOwner() {
		return this.shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public Set<TDistributor> getTDistributors() {
		return this.TDistributors;
	}

	public void setTDistributors(Set<TDistributor> TDistributors) {
		this.TDistributors = TDistributors;
	}
	
}