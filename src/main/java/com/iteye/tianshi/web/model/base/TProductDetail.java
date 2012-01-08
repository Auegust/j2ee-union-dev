package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.util.Date;


/**
 * 经销商销售明细表
 * 
 */
@Entity
@Table(name="t_product_list")
public class TProductDetail extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="check_flag")
	private String checkFlag;

	@Column(name="check_man")
	private String checkMan;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String creator;

	private String remark;

	@Column(name="sale_number")
	private Integer saleNumber;

	@Column(name="sale_price")
	private Integer salePrice;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="sale_time")
	private Date saleTime;

    @ManyToOne
	@JoinColumn(name="product_id")
	private TProductInfo TProductInfo;

    @ManyToOne
	@JoinColumn(name="distributor_id")
	private TDistributor TDistributor;

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

	public Integer getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Date getSaleTime() {
		return this.saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public TProductInfo getTProductInfo() {
		return this.TProductInfo;
	}

	public void setTProductInfo(TProductInfo TProductInfo) {
		this.TProductInfo = TProductInfo;
	}
	
	public TDistributor getTDistributor() {
		return this.TDistributor;
	}

	public void setTDistributor(TDistributor TDistributor) {
		this.TDistributor = TDistributor;
	}
	
}