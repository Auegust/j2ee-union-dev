package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 奖金历史核算表
 * 
 */
@Entity
@Table(name="t_distributor_bouns_his")
public class TDistributorBounsHis extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name="res_id")
//    @GeneratedValue(generator = "tableGenerator")     
//	@GenericGenerator(name = "tableGenerator", strategy="assigned")
//	private Long id;
	
	@Column(name="adjust_bouns")
	private Double adjustBouns;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="bouns_date")
	private Date bounsDate;

	@Column(name="check_flag")
	private String checkFlag;

	@Column(name="check_man")
	private String checkMan;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="check_time")
	private Date checkTime;

	@Column(name="computer_fee")
	private Double computerFee;

	@Column(name="direct_bouns")
	private Double directBouns;

	@Column(name="distributor_id")
	private Long distributorId;

	@Column(name="distributor_code")
	private String  distributorCode;
	
	@Column(name="honor_bouns")
	private Double honorBouns;

	@Column(name="indirect_bouns")
	private Double indirectBouns;

	@Column(name="internatial_bouns")
	private Double internatialBouns;

	@Column(name="leader_bouns")
	private Double leaderBouns;

	@Column(name="rank_id")
	private Long rankId;

	private String remark;

	@Column(name="special_bouns")
	private Double specialBouns;

	//历史奖金表月份的标志位 
	@Column(name="batch_no")
	private Integer batchNo;
	
	private Double tax;

    public TDistributorBounsHis() {
    }

	public Double getAdjustBouns() {
		return this.adjustBouns;
	}

	public void setAdjustBouns(Double adjustBouns) {
		this.adjustBouns = adjustBouns;
	}

	public Date getBounsDate() {
		return this.bounsDate;
	}

	public void setBounsDate(Date bounsDate) {
		this.bounsDate = bounsDate;
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

	public Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Double getComputerFee() {
		return this.computerFee;
	}

	public void setComputerFee(Double computerFee) {
		this.computerFee = computerFee;
	}

	public Double getDirectBouns() {
		return this.directBouns;
	}

	public void setDirectBouns(Double directBouns) {
		this.directBouns = directBouns;
	}

	public Long getDistributorId() {
		return this.distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public Double getHonorBouns() {
		return this.honorBouns;
	}

	public void setHonorBouns(Double honorBouns) {
		this.honorBouns = honorBouns;
	}

	public Double getIndirectBouns() {
		return this.indirectBouns;
	}

	public void setIndirectBouns(Double indirectBouns) {
		this.indirectBouns = indirectBouns;
	}

	public Double getInternatialBouns() {
		return this.internatialBouns;
	}

	public void setInternatialBouns(Double internatialBouns) {
		this.internatialBouns = internatialBouns;
	}

	public Double getLeaderBouns() {
		return this.leaderBouns;
	}

	public void setLeaderBouns(Double leaderBouns) {
		this.leaderBouns = leaderBouns;
	}

	public Long getRankId() {
		return this.rankId;
	}

	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSpecialBouns() {
		return this.specialBouns;
	}

	public void setSpecialBouns(Double specialBouns) {
		this.specialBouns = specialBouns;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getBatchNo() {
		return batchNo;
	}
}