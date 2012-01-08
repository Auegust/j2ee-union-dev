package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;

import com.iteye.tianshi.core.web.model.BaseEntity;

import java.util.Set;


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

	@OneToMany(mappedBy="TDistributorRank")
	private Set<TBounsConf> TBounsConfs;

	@OneToMany(mappedBy="TDistributorRank")
	private Set<TBounsLeaderConf> TBounsLeaderConfs;

	@OneToMany(mappedBy="TDistributorRank")
	private Set<TDistributor> TDistributors;

	@OneToMany(mappedBy="TDistributorRank")
	private Set<TDistributorBoun> TDistributorBouns;

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

	public Set<TBounsConf> getTBounsConfs() {
		return this.TBounsConfs;
	}

	public void setTBounsConfs(Set<TBounsConf> TBounsConfs) {
		this.TBounsConfs = TBounsConfs;
	}
	
	public Set<TBounsLeaderConf> getTBounsLeaderConfs() {
		return this.TBounsLeaderConfs;
	}

	public void setTBounsLeaderConfs(Set<TBounsLeaderConf> TBounsLeaderConfs) {
		this.TBounsLeaderConfs = TBounsLeaderConfs;
	}
	
	public Set<TDistributor> getTDistributors() {
		return this.TDistributors;
	}

	public void setTDistributors(Set<TDistributor> TDistributors) {
		this.TDistributors = TDistributors;
	}
	
	public Set<TDistributorBoun> getTDistributorBouns() {
		return this.TDistributorBouns;
	}

	public void setTDistributorBouns(Set<TDistributorBoun> TDistributorBouns) {
		this.TDistributorBouns = TDistributorBouns;
	}
	
}