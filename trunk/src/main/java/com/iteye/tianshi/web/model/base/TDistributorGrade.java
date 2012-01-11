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
 * 
 * 经销商业绩表
 */
@Entity
@Table(name="t_distributor_grade")
public class TDistributorGrade  extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="accu_achieve")
	private Integer accuAchieve;

	@Column(name="accu_p_achieve")
	private Integer accuPAchieve;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="achieve_date")
	private Date achieveDate;

	@Column(name="cell_achieve")
	private Integer cellAchieve;

	@Column(name="check_flag")
	private String checkFlag;

	@Column(name="check_man")
	private String checkMan;

	@Column(name="direct_achieve")
	private Integer directAchieve;

	@Column(name="indirect_achieve")
	private Integer indirectAchieve;

	@Column(name="net_achieve")
	private Integer netAchieve;

	@Column(name="person_achieve")
	private Integer personAchieve;

	private String remark;

	@Column(name="distributor_id")
	private Long  distributorId;

    public TDistributorGrade() {
    }


	public Integer getAccuAchieve() {
		return this.accuAchieve;
	}

	public void setAccuAchieve(Integer accuAchieve) {
		this.accuAchieve = accuAchieve;
	}

	public Integer getAccuPAchieve() {
		return this.accuPAchieve;
	}

	public void setAccuPAchieve(Integer accuPAchieve) {
		this.accuPAchieve = accuPAchieve;
	}

	public Date getAchieveDate() {
		return this.achieveDate;
	}

	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}

	public Integer getCellAchieve() {
		return this.cellAchieve;
	}

	public void setCellAchieve(Integer cellAchieve) {
		this.cellAchieve = cellAchieve;
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

	public Integer getDirectAchieve() {
		return this.directAchieve;
	}

	public void setDirectAchieve(Integer directAchieve) {
		this.directAchieve = directAchieve;
	}

	public Integer getIndirectAchieve() {
		return this.indirectAchieve;
	}

	public void setIndirectAchieve(Integer indirectAchieve) {
		this.indirectAchieve = indirectAchieve;
	}

	public Integer getNetAchieve() {
		return this.netAchieve;
	}

	public void setNetAchieve(Integer netAchieve) {
		this.netAchieve = netAchieve;
	}

	public Integer getPersonAchieve() {
		return this.personAchieve;
	}

	public void setPersonAchieve(Integer personAchieve) {
		this.personAchieve = personAchieve;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}


	public Long getDistributorId() {
		return distributorId;
	}
	
}