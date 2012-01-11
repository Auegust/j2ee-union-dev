package com.iteye.tianshi.web.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.iteye.tianshi.core.web.model.BaseEntity;


/**
 * 经销商职级表
 * 
 */
@Entity
@Table(name="t_distributor_rank")
public class TDistributorRank extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="rank_name")
	private String rankName;

	@Column(name="rank_status")
	private String rankStatus;

	private String remark;

    public TDistributorRank() {
    }

	public String getRankName() {
		return this.rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getRankStatus() {
		return this.rankStatus;
	}

	public void setRankStatus(String rankStatus) {
		this.rankStatus = rankStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}