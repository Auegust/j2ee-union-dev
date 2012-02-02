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
	private String achieveP; //成就奖

	@Column(name="direct_p")
	private String directP; //直接奖

	@Column(name="honor_p")
	private String honorP;  //荣衔奖

	@Column(name="indirect_p")
	private String indirectP;  //间接奖

	private String remark;

	@Column(name="special_p")
	private String specialP; //特别奖

	@Column(name="rank_id")
	private Long rankId;

	@Transient
	private String rankId_Name;
	
	@Column(name="w_1")
	private String w1;

	@Column(name="w_2")
	private String w2;

	@Column(name="w_3")
	private String w3;

	@Column(name="w_4")
	private String w4;

	@Column(name="w_5")
	private String w5;

	@Column(name="w_6")
	private String w6;

	@Column(name="w_7")
	private String w7;

	@Column(name="w_8")
	private String w8;
	
    public Double getW1() {
		return Double.valueOf(this.w1)/100;
	}
	public void setW1(String w1) {
		this.w1 = w1;
	}
	public Double getW2() {
		return Double.valueOf(this.w2)/100;
	}
	public void setW2(String w2) {
		this.w2 = w2;
	}
	public Double getW3() {
		return Double.valueOf(this.w3)/100;
	}
	public void setW3(String w3) {
		this.w3 = w3;
	}
	public Double getW4() {
		return Double.valueOf(this.w4)/100;
	}
	public void setW4(String w4) {
		this.w4 = w4;
	}
	public Double getW5() {
		return Double.valueOf(this.w5)/100;
	}
	public void setW5(String w5) {
		this.w5 = w5;
	}
	public Double getW6() {
		return Double.valueOf(this.w6)/100;
	}
	public void setW6(String w6) {
		this.w6 = w6;
	}
	public Double getW7() {
		return Double.valueOf(this.w7)/100;
	}
	public void setW7(String w7) {
		this.w7 = w7;
	}
	public Double getW8() {
		return Double.valueOf(this.w8)/100;
	}
	public void setW8(String w8) {
		this.w8 = w8;
	}
	public TBounsConf() {
    }
	
	public Double getAchieveP() {
		return Double.valueOf(this.achieveP)/100;
	}

	public void setAchieveP(String achieveP) {
		this.achieveP = achieveP;
	}

	public Double getDirectP() {
		return  Double.valueOf(this.directP)/100;
	}

	public void setDirectP(String directP) {
		this.directP = directP;
	}

	public Double getHonorP() {
		return Double.valueOf(this.honorP)/100;
	}

	public void setHonorP(String honorP) {
		this.honorP = honorP;
	}

	public Double getIndirectP() {
		return  Double.valueOf(this.indirectP)/100;
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

	public Double getSpecialP() {
		return Double.valueOf(this.specialP)/100;
	}

	public void setSpecialP(String specialP) {
		this.specialP = specialP;
	}
	public Long getRankId() {
		return rankId;
	}
	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}
	public void setRankId_Name(String rankId_Name) {
		this.rankId_Name = rankId_Name;
	}
	public String getRankId_Name() {
		return rankId_Name;
	}
	
}