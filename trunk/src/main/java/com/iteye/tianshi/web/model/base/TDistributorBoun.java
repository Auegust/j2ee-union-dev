package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 奖金核算表
 * 
 */
@Entity
@Table(name="t_distributor_bouns")
public class TDistributorBoun extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="adjust_bouns")
	private BigDecimal adjustBouns;

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
	private BigDecimal computerFee;

	@Column(name="direct_bouns")
	private BigDecimal directBouns;

	@Column(name="honor_bouns")
	private BigDecimal honorBouns;

	@Column(name="indirect_bouns")
	private BigDecimal indirectBouns;

	@Column(name="internatial_bouns")
	private BigDecimal internatialBouns;

	@Column(name="leader_bouns")
	private BigDecimal leaderBouns;

	private String remark;

	@Column(name="special_bouns")
	private BigDecimal specialBouns;

	private BigDecimal tax;

    @ManyToOne
	@JoinColumn(name="rank_id")
	private TDistributorRank TDistributorRank;

    @ManyToOne
	@JoinColumn(name="distributor_id")
	private TDistributor TDistributor;

    public TDistributorBoun() {
    }

	public BigDecimal getAdjustBouns() {
		return this.adjustBouns;
	}

	public void setAdjustBouns(BigDecimal adjustBouns) {
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

	public BigDecimal getComputerFee() {
		return this.computerFee;
	}

	public void setComputerFee(BigDecimal computerFee) {
		this.computerFee = computerFee;
	}

	public BigDecimal getDirectBouns() {
		return this.directBouns;
	}

	public void setDirectBouns(BigDecimal directBouns) {
		this.directBouns = directBouns;
	}

	public BigDecimal getHonorBouns() {
		return this.honorBouns;
	}

	public void setHonorBouns(BigDecimal honorBouns) {
		this.honorBouns = honorBouns;
	}

	public BigDecimal getIndirectBouns() {
		return this.indirectBouns;
	}

	public void setIndirectBouns(BigDecimal indirectBouns) {
		this.indirectBouns = indirectBouns;
	}

	public BigDecimal getInternatialBouns() {
		return this.internatialBouns;
	}

	public void setInternatialBouns(BigDecimal internatialBouns) {
		this.internatialBouns = internatialBouns;
	}

	public BigDecimal getLeaderBouns() {
		return this.leaderBouns;
	}

	public void setLeaderBouns(BigDecimal leaderBouns) {
		this.leaderBouns = leaderBouns;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSpecialBouns() {
		return this.specialBouns;
	}

	public void setSpecialBouns(BigDecimal specialBouns) {
		this.specialBouns = specialBouns;
	}

	public BigDecimal getTax() {
		return this.tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public TDistributorRank getTDistributorRank() {
		return this.TDistributorRank;
	}

	public void setTDistributorRank(TDistributorRank TDistributorRank) {
		this.TDistributorRank = TDistributorRank;
	}
	
	public TDistributor getTDistributor() {
		return this.TDistributor;
	}

	public void setTDistributor(TDistributor TDistributor) {
		this.TDistributor = TDistributor;
	}
	
}