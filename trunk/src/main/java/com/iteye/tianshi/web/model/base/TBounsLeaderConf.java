package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.math.BigDecimal;


/**
 * 领导奖比例配置表
 * 
 */
@Entity
@Table(name="t_bouns_leader_conf")
public class TBounsLeaderConf extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String remark;

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

    @ManyToOne
	@JoinColumn(name="rank_id")
	private TDistributorRank TDistributorRank;

    public TBounsLeaderConf() {
    }
    
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getW1() {
		return this.w1;
	}

	public void setW1(BigDecimal w1) {
		this.w1 = w1;
	}

	public BigDecimal getW2() {
		return this.w2;
	}

	public void setW2(BigDecimal w2) {
		this.w2 = w2;
	}

	public BigDecimal getW3() {
		return this.w3;
	}

	public void setW3(BigDecimal w3) {
		this.w3 = w3;
	}

	public BigDecimal getW4() {
		return this.w4;
	}

	public void setW4(BigDecimal w4) {
		this.w4 = w4;
	}

	public BigDecimal getW5() {
		return this.w5;
	}

	public void setW5(BigDecimal w5) {
		this.w5 = w5;
	}

	public BigDecimal getW6() {
		return this.w6;
	}

	public void setW6(BigDecimal w6) {
		this.w6 = w6;
	}

	public BigDecimal getW7() {
		return this.w7;
	}

	public void setW7(BigDecimal w7) {
		this.w7 = w7;
	}

	public BigDecimal getW8() {
		return this.w8;
	}

	public void setW8(BigDecimal w8) {
		this.w8 = w8;
	}

	public TDistributorRank getTDistributorRank() {
		return this.TDistributorRank;
	}

	public void setTDistributorRank(TDistributorRank TDistributorRank) {
		this.TDistributorRank = TDistributorRank;
	}
	
}