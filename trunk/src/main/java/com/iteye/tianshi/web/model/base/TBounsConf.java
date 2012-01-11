package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@Column(name="rank_id")
	private Long rankId;

	@Column(name="w_1")
	private BigDecimal w1;

	@Column(name="w_2")
	private BigDecimal w2;

	@Column(name="w_3")
	private BigDecimal w3;

	@Column(name="w_4")
	private BigDecimal w4;

	@Column(name="w_5")
	private BigDecimal w5;

	@Column(name="w_6")
	private BigDecimal w6;

	@Column(name="w_7")
	private BigDecimal w7;

	@Column(name="w_8")
	private BigDecimal w8;
	
    public BigDecimal getW1() {
		return w1;
	}
	public void setW1(BigDecimal w1) {
		this.w1 = w1;
	}
	public BigDecimal getW2() {
		return w2;
	}
	public void setW2(BigDecimal w2) {
		this.w2 = w2;
	}
	public BigDecimal getW3() {
		return w3;
	}
	public void setW3(BigDecimal w3) {
		this.w3 = w3;
	}
	public BigDecimal getW4() {
		return w4;
	}
	public void setW4(BigDecimal w4) {
		this.w4 = w4;
	}
	public BigDecimal getW5() {
		return w5;
	}
	public void setW5(BigDecimal w5) {
		this.w5 = w5;
	}
	public BigDecimal getW6() {
		return w6;
	}
	public void setW6(BigDecimal w6) {
		this.w6 = w6;
	}
	public BigDecimal getW7() {
		return w7;
	}
	public void setW7(BigDecimal w7) {
		this.w7 = w7;
	}
	public BigDecimal getW8() {
		return w8;
	}
	public void setW8(BigDecimal w8) {
		this.w8 = w8;
	}
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
	public Long getRankId() {
		return rankId;
	}
	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}
	
}