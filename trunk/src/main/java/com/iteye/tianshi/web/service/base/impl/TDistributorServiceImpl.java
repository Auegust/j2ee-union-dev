package com.iteye.tianshi.web.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.tianshi.core.jdbc.CustomSQLUtil;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDistributorBounDao;
import com.iteye.tianshi.web.dao.base.TDistributorBounsHisDao;
import com.iteye.tianshi.web.dao.base.TDistributorDao;
import com.iteye.tianshi.web.dao.base.TDistributorGradeDao;
import com.iteye.tianshi.web.dao.base.TDistributorGradeHisDao;
import com.iteye.tianshi.web.dao.base.TProductDetailDao;
import com.iteye.tianshi.web.model.base.TDistributor;
import com.iteye.tianshi.web.model.base.TDistributorBoun;
import com.iteye.tianshi.web.model.base.TDistributorBounsHis;
import com.iteye.tianshi.web.model.base.TDistributorGrade;
import com.iteye.tianshi.web.model.base.TDistributorGradeHis;
import com.iteye.tianshi.web.model.base.TProductDetail;
import com.iteye.tianshi.web.service.base.TDistributorService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDistributorServiceImpl extends BaseServiceImpl<TDistributor, Long> implements TDistributorService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDistributorDao distributorDao;
	@Autowired
	private TProductDetailDao productOrderDao;
	@Autowired
	private TDistributorBounDao distributorBounDao;
	@Autowired
	private TDistributorBounsHisDao distributorBounHisDao;
	@Autowired
	private TDistributorGradeDao  distributorGradeDao;
	@Autowired
	private TDistributorGradeHisDao  distributorGradeHisDao;
	//~ Constructors ===================================================================================================

	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDistributor, Long> getGenericDao() {
		return this.distributorDao;
	}

	@Override
	public boolean hasZeroRecord() {
		String sql = CustomSQLUtil.get(SQL_COUNT);
		int count = distributorDao.getJdbcTemplate().queryForInt(sql);
		if(count == 0){
			return true;
		}
		return false;
	}

	@Override
	public List<TDistributor> findAllChildrenDistributors(Long id,Integer floors) {
		StrBuilder sql = new StrBuilder("select * FROM tianshi.t_distributor WHERE FIND_IN_SET(res_id,tianshi.getChildLst(").append(id+")) ");
		if (floors!=null) {
			sql.append("and floors>").append(floors);
		}
		List<Map<String, Object>> templist = this.distributorDao.getJdbcTemplate().queryForList(sql.toString());
		List<TDistributor> list = new ArrayList<TDistributor>(templist.size());
		for(Map<String,Object> map : templist){
			TDistributor tDistributor = new TDistributor();
			tDistributor.setId(Long.valueOf(map.get("res_id").toString()));
			tDistributor.setShopId(Long.valueOf(map.get("shop_id").toString()));
			tDistributor.setRankId(Long.valueOf(map.get("rank_id").toString()));
			tDistributor.setDistributorCode(map.get("distributor_code").toString());
			tDistributor.setDistributorName(map.get("distributor_name").toString());
			tDistributor.setSponsorId(Long.valueOf(map.get("sponsor_id").toString()));
			tDistributor.setCreateTime((Date)map.get("create_time"));
			tDistributor.setAddress(map.get("address").toString());
			tDistributor.setTelephone(map.get("telephone").toString());
			tDistributor.setRemark(map.get("remark").toString());
			tDistributor.setSponsorCode(map.get("sponsor_code").toString());
			tDistributor.setFloors(Integer.valueOf(map.get("floors").toString()));
			list.add(tDistributor);
			map = null;
			tDistributor = null;
		}
		templist = null;
		return list;
	}

	@Override
	public List<TDistributor> findAllDirChildrenDistributors(Long id,
			Integer floors) {
		StrBuilder sql = new StrBuilder("select * FROM tianshi.t_distributor WHERE FIND_IN_SET(res_id,tianshi.getChildLst(").append(id+")) ");
		if (floors!=null) {
			sql.append("and floors=").append(floors);
		}
		List<Map<String, Object>> templist = this.distributorDao.getJdbcTemplate().queryForList(sql.toString());
		List<TDistributor> list = new ArrayList<TDistributor>(templist.size());
		for(Map<String,Object> map : templist){
			TDistributor tDistributor = new TDistributor();
			tDistributor.setId(Long.valueOf(map.get("res_id").toString()));
			tDistributor.setShopId(Long.valueOf(map.get("shop_id").toString()));
			tDistributor.setRankId(Long.valueOf(map.get("rank_id").toString()));
			tDistributor.setDistributorCode(map.get("distributor_code").toString());
			tDistributor.setDistributorName(map.get("distributor_name").toString());
			tDistributor.setSponsorId(Long.valueOf(map.get("sponsor_id").toString()));
			tDistributor.setCreateTime((Date)map.get("create_time"));
			tDistributor.setAddress(map.get("address").toString());
			tDistributor.setTelephone(map.get("telephone").toString());
			tDistributor.setRemark(map.get("remark").toString());
			tDistributor.setSponsorCode(map.get("sponsor_code").toString());
			tDistributor.setFloors(Integer.valueOf(map.get("floors").toString()));
			list.add(tDistributor);
			map = null;
			tDistributor = null;
		}
		templist = null;
		return list;
	}

	@Override
	public void deleteAllCascadeTable(Long id) {
		List<TProductDetail> orders = productOrderDao.findByProperty("distributorId", id);
		if(!orders.isEmpty()){
			productOrderDao.delete(orders);
		}
		orders = null;
		
		List<TDistributorBoun> bouns = distributorBounDao.findByProperty("distributorId", id);
		if(!bouns.isEmpty()){
			distributorBounDao.delete(bouns);
		}
		bouns = null;
		//历史表
		List<TDistributorBounsHis> bounsHis = distributorBounHisDao.findByProperty("distributorId", id);
		if(!bounsHis.isEmpty()){
			distributorBounHisDao.delete(bounsHis);
		}
		bounsHis = null;
		
		List<TDistributorGrade> grade = distributorGradeDao.findByProperty("distributorId", id);
		if(!grade.isEmpty()){
			distributorGradeDao.delete(grade);
		}
		grade = null;
		//历史表
		List<TDistributorGradeHis> gradeHis = distributorGradeHisDao.findByProperty("distributorId", id);
		if(!gradeHis.isEmpty()){
			distributorGradeHisDao.delete(gradeHis);
		}
		gradeHis = null;
	}
	
}
