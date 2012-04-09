package com.iteye.tianshi.web.service.base;

import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.web.service.BaseService;
import com.iteye.tianshi.web.model.base.TDistributorGrade;

/**
 * 经销商业绩表
 * @datetime 2010-8-9 上午10:34:58
 * @author  Jackson
 */
public interface TDistributorGradeService extends BaseService<TDistributorGrade, Long> {
	
	public ResponseData mainCompute(String  endDate);

}
