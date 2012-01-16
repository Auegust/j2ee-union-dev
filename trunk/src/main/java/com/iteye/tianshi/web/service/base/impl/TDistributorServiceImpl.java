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
import com.iteye.tianshi.web.dao.base.TDistributorDao;
import com.iteye.tianshi.web.model.base.TDistributor;
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
	
}
