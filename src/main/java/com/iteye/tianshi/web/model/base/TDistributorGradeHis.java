package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.iteye.tianshi.core.util.ConstantUtil;
import com.iteye.tianshi.core.web.model.BaseEntity;

/**
 * 
 * 经销商业绩历史表
 */
@Entity
@Table(name = "t_distributor_grade_his")
public class TDistributorGradeHis extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//累计业绩
	@Column(name = "accu_achieve")
	private Double accuAchieve=0d;
	
	//累计个人业绩
	@Column(name = "accu_p_achieve")
	private Double accuPAchieve=0d;
	
	//日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "achieve_date")
	private Date achieveDate;
	
	//小组业绩
	@Column(name = "cell_achieve")
	private Double cellAchieve=0d;
	
	//个人业绩奖金
	@Column(name = "bonus_achieve")
	private Double bonusAchieve=0d;
	
	//审核标志
	@Column(name = "check_flag")
	private String checkFlag;
	
	//审核人员
	@Column(name = "check_man")
	private String checkMan;
	
	//直接业绩
	@Column(name = "direct_achieve")
	private Double directAchieve=0d;
	
	//间接业绩
	@Column(name = "indirect_achieve")
	private Double indirectAchieve=0d;
	
	//整网业绩
	@Column(name = "net_achieve")
	private Double netAchieve=0D;
	
	//个人业绩
	@Column(name = "person_achieve")
	private Double personAchieve=0d;
	
	//备注
	private String remark;
	
	//经销商ID
	@Column(name = "distributor_id")
	private Long distributorId;
	
	//经销商编号
	@Column(name = "distributor_code")
	private String distributorCode;
	
	//历史业绩表月份的标志位 
	@Column(name="batch_no")
	private Integer batchNo;
	
	//默认1星
	private Long rank = ConstantUtil._lev_1;
	
	//当月最大消费
	private Double maxChange = 0d;
	
	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public Double getMaxChange() {
		return maxChange;
	}

	public void setMaxChange(Double maxChange) {
		this.maxChange = maxChange;
	}

	@Column(name = "floors")
	private int floors;
	
	public TDistributorGradeHis() {
	}
	
	/**
	 * 获取累计业绩
	 */
	public Double getAccuAchieve() {
		return this.accuAchieve;
	}
	
	/**
	 * 设置累计业绩
	 */
	public void setAccuAchieve(Double accuAchieve) {
		this.accuAchieve = accuAchieve;
	}

	/**
	 * 获取个人累计业绩
	 */
	public Double getAccuPAchieve() {
		return this.accuPAchieve;
	}
	
	/**
	 * 设置个人累计业绩
	 */
	public void setAccuPAchieve(Double accuPAchieve) {
		this.accuPAchieve = accuPAchieve;
	}
	
	/**
	 * 获取业绩日期
	 * @dateime 2012-1-16 下午01:59:30
	 */
	public Date getAchieveDate() {
		return this.achieveDate;
	}
	
	/**
	 * 设置业绩日期
	 */
	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}
	
	/**
	 * 获取小组业绩
	 */
	public Double getCellAchieve() {
		return this.cellAchieve;
	}
	
	/**
	 * 设置小组业绩
	 */
	public void setCellAchieve(Double cellAchieve) {
		this.cellAchieve = cellAchieve;
	}
	
	/**
	 * 获取审核标志
	 */
	public String getCheckFlag() {
		return this.checkFlag;
	}
	
	/**
	 * 设置审核标志
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	/**
	 * 获取审核人员
	 */
	public String getCheckMan() {
		return this.checkMan;
	}
	
	/**
	 * 设置审核人员
	 */
	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	
	/**
	 * 获取直接业绩
	 */
	public Double getDirectAchieve() {
		return this.directAchieve;
	}
	
	/**
	 * 设置直接业绩
	 */
	public void setDirectAchieve(Double directAchieve) {
		this.directAchieve = directAchieve;
	}
	
	/**
	 * 获取间接业绩
	 */
	public Double getIndirectAchieve() {
		return this.indirectAchieve;
	}
	
	/**
	 * 设置间接业绩
	 */
	public void setIndirectAchieve(Double indirectAchieve) {
		this.indirectAchieve = indirectAchieve;
	}

	/**
	 * 获取整网业绩
	 */
	public Double getNetAchieve() {
		return this.netAchieve;
	}
	
	/**
	 * 设置整网业绩
	 */
	public void setNetAchieve(Double netAchieve) {
		this.netAchieve = netAchieve;
	}
	
	/**
	 * 获取个人业绩
	 */
	public Double getPersonAchieve() {
		return this.personAchieve;
	}
	
	/**
	 * 设置个人业绩
	 */
	public void setPersonAchieve(Double personAchieve) {
		this.personAchieve = personAchieve;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 获取经销商ID
	 */
	public Long getDistributorId() {
		return distributorId;
	}
	
	/**
	 * 设置经销商ID
	 */
	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}
	
	/**
	 * 获取经销商编号
	 */
	public String getDistributorCode() {
		return distributorCode;
	}
	
	/**
	 * 设置经销商编号
	 */
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	
	/**
	 * 获取经销商层级
	 */
	public int getFloors() {
		return floors;
	}
	
	/**
	 * 设置经销商层级
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}

	public void setBonusAchieve(Double bonusAchieve) {
		this.bonusAchieve = bonusAchieve;
	}

	public Double getBonusAchieve() {
		return bonusAchieve;
	}

	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getBatchNo() {
		return batchNo;
	}
}