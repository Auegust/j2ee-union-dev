package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 直接/间接奖核算配置表
 * 
 */
@Entity
@Table(name="t_bouns_conf")
public class TBounsConf extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="achieve_p")
	private BigDecimal achieveP;

	@Column(name="direct_p")
	private BigDecimal directP;

	@Column(name="honor_p")
	private BigDecimal honorP;

	@Column(name="indirect_p")
	private String indirectP;

	private String remark;

	@Column(name="special_p")
	private BigDecimal specialP;

    @ManyToOne
	@JoinColumn(name="rank_id")
	private TDistributorRank TDistributorRank;

    public TBounsConf() {
    }
	public BigDecimal getAchieveP() {
		return this.achieveP;
	}

	public void setAchieveP(BigDecimal achieveP) {
		this.achieveP = achieveP;
	}

	public BigDecimal getDirectP() {
		return this.directP;
	}

	public void setDirectP(BigDecimal directP) {
		this.directP = directP;
	}

	public BigDecimal getHonorP() {
		return this.honorP;
	}

	public void setHonorP(BigDecimal honorP) {
		this.honorP = honorP;
	}

	public String getIndirectP() {
		return this.indirectP;
	}

	public void setIndirectP(String indirectP) {
		this.indirectP = indirectP;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSpecialP() {
		return this.specialP;
	}

	public void setSpecialP(BigDecimal specialP) {
		this.specialP = specialP;
	}

	public TDistributorRank getTDistributorRank() {
		return this.TDistributorRank;
	}

	public void setTDistributorRank(TDistributorRank TDistributorRank) {
		this.TDistributorRank = TDistributorRank;
	}
	
}