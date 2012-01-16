package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.iteye.tianshi.core.web.model.BaseEntity;

/**
 * 
 * 经销商业绩表
 */
@Entity
@Table(name = "t_distributor_grade")
public class TDistributorGrade extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//累计业绩
	@Column(name = "accu_achieve")
	private double accuAchieve;
	
	//累计个人业绩
	@Column(name = "accu_p_achieve")
	private double accuPAchieve;
	
	//日期
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "achieve_date")
	private Date achieveDate;
	
	//小组业绩
	@Column(name = "cell_achieve")
	private double cellAchieve;
	
	//审核标志
	@Column(name = "check_flag")
	private String checkFlag;
	
	//审核人员
	@Column(name = "check_man")
	private String checkMan;
	
	//直接业绩
	@Column(name = "direct_achieve")
	private double directAchieve;
	
	//间接业绩
	@Column(name = "indirect_achieve")
	private double indirectAchieve;
	
	//整网业绩
	@Column(name = "net_achieve")
	private double netAchieve;
	
	//个人业绩
	@Column(name = "person_achieve")
	private double personAchieve;
	
	//备注
	private String remark;
	
	//经销商ID
	@Column(name = "distributor_id")
	private Long distributorId;
	
	//经销商编号
	@Column(name = "distributor_code")
	private String distributorCode;
	
	@Transient
	private String distributorName;
	
	//经销商编号
	@Column(name = "floors")
	private int floors;

	/**
	 * 获取累计业绩
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getAccuAchieve() {
		return this.accuAchieve;
	}
	
	/**
	 * 设置累计业绩
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setAccuAchieve(double accuAchieve) {
		this.accuAchieve = accuAchieve;
	}

	/**
	 * 获取个人累计业绩
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getAccuPAchieve() {
		return this.accuPAchieve;
	}
	
	/**
	 * 设置个人累计业绩
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setAccuPAchieve(double accuPAchieve) {
		this.accuPAchieve = accuPAchieve;
	}
	
	/**
	 * 获取业绩日期
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public Date getAchieveDate() {
		return this.achieveDate;
	}
	
	/**
	 * 设置业绩日期
	 * @dateime 2012-1-16 下午01:59:30
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setAchieveDate(Date achieveDate) {
		this.achieveDate = achieveDate;
	}
	
	/**
	 * 获取小组业绩
	 * @dateime 2012-1-16 下午02:01:26
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getCellAchieve() {
		return this.cellAchieve;
	}
	
	/**
	 * 设置小组业绩
	 * @dateime 2012-1-16 下午02:01:26
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setCellAchieve(double cellAchieve) {
		this.cellAchieve = cellAchieve;
	}
	
	/**
	 * 获取审核标志
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public String getCheckFlag() {
		return this.checkFlag;
	}
	
	/**
	 * 设置审核标志
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
	/**
	 * 获取审核人员
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public String getCheckMan() {
		return this.checkMan;
	}
	
	/**
	 * 设置审核人员
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	
	/**
	 * 获取直接业绩
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getDirectAchieve() {
		return this.directAchieve;
	}
	
	/**
	 * 设置直接业绩
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setDirectAchieve(double directAchieve) {
		this.directAchieve = directAchieve;
	}
	
	/**
	 * 获取间接业绩
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getIndirectAchieve() {
		return this.indirectAchieve;
	}
	
	/**
	 * 设置间接业绩
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setIndirectAchieve(double indirectAchieve) {
		this.indirectAchieve = indirectAchieve;
	}

	/**
	 * 获取整网业绩
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getNetAchieve() {
		return this.netAchieve;
	}
	
	/**
	 * 设置整网业绩
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setNetAchieve(double netAchieve) {
		this.netAchieve = netAchieve;
	}
	
	/**
	 * 获取个人业绩
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public double getPersonAchieve() {
		return this.personAchieve;
	}
	
	/**
	 * 设置个人业绩
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setPersonAchieve(double personAchieve) {
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
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public Long getDistributorId() {
		return distributorId;
	}
	
	/**
	 * 设置经销商ID
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}
	
	/**
	 * 获取经销商编号
	 * @dateime 2012-1-16 下午02:01:57
	 * @author chenfengming456@163.com
	 * @return
	 */
	public String getDistributorCode() {
		return distributorCode;
	}
	
	/**
	 * 设置经销商编号
	 * @dateime 2012-1-16 下午02:03:44
	 * @author chenfengming456@163.com
	 * @param distributorCode
	 */
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	
	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	
	/**
	 * 获取经销商层级
	 * @dateime 2012-1-16 下午02:30:16
	 * @author chenfengming456@163.com
	 * @param floors
	 */
	public int getFloors() {
		return floors;
	}
	
	/**
	 * 设置经销商层级
	 * @dateime 2012-1-16 下午02:30:16
	 * @author chenfengming456@163.com
	 * @param floors
	 */
	public void setFloors(int floors) {
		this.floors = floors;
	}
}