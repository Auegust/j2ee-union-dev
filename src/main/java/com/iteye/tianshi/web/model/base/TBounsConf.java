package com.iteye.tianshi.web.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 奖金配置表
 * 
 */
@Entity
@Table(name="t_bouns_conf")
public class TBounsConf extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="achieve_p")
	private Double achieveP; //成就奖

	@Column(name="direct_p")
	private Double directP; //直接奖

	@Column(name="honor_p")
	private Double honorP;  //荣衔奖

	@Column(name="indirect_p")
	private String  indirectP;  //间接奖

	public String getIndirectP() {
		return indirectP;
	}

	public void setIndirectP(String indirectP) {
		this.indirectP = indirectP;
	}

	private String remark;

	@Column(name="special_p")
	private Double specialP; //特别奖

	@Column(name="rank_id")
	private Long rankId;

	@Transient
	private String rankId_Name;
	
	@Column(name="w_1")
	private Double w1;

	@Column(name="w_2")
	private Double w2;

	@Column(name="w_3")
	private Double w3;

	@Column(name="w_4")
	private Double w4;

	@Column(name="w_5")
	private Double w5;

	@Column(name="w_6")
	private Double w6;

	@Column(name="w_7")
	private Double w7;

	@Column(name="w_8")
	private Double w8;

	public Double getAchieveP() {
		return achieveP;
	}

	public void setAchieveP(Double achieveP) {
		this.achieveP = achieveP;
	}

	public Double getDirectP() {
		return directP;
	}

	public void setDirectP(Double directP) {
		this.directP = directP;
	}

	public Double getHonorP() {
		return honorP;
	}

	public void setHonorP(Double honorP) {
		this.honorP = honorP;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSpecialP() {
		return specialP;
	}

	public void setSpecialP(Double specialP) {
		this.specialP = specialP;
	}

	public Long getRankId() {
		return rankId;
	}

	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}

	public String getRankId_Name() {
		return rankId_Name;
	}

	public void setRankId_Name(String rankId_Name) {
		this.rankId_Name = rankId_Name;
	}

	public Double getW1() {
		return w1;
	}

	public void setW1(Double w1) {
		this.w1 = w1;
	}

	public Double getW2() {
		return w2;
	}

	public void setW2(Double w2) {
		this.w2 = w2;
	}

	public Double getW3() {
		return w3;
	}

	public void setW3(Double w3) {
		this.w3 = w3;
	}

	public Double getW4() {
		return w4;
	}

	public void setW4(Double w4) {
		this.w4 = w4;
	}

	public Double getW5() {
		return w5;
	}

	public void setW5(Double w5) {
		this.w5 = w5;
	}

	public Double getW6() {
		return w6;
	}

	public void setW6(Double w6) {
		this.w6 = w6;
	}

	public Double getW7() {
		return w7;
	}

	public void setW7(Double w7) {
		this.w7 = w7;
	}

	public Double getW8() {
		return w8;
	}

	public void setW8(Double w8) {
		this.w8 = w8;
	}
}