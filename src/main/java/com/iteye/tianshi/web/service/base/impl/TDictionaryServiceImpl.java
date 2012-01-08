package com.iteye.tianshi.web.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iteye.tianshi.core.util.DictionaryHolder;
import com.iteye.tianshi.core.web.dao.GenericDao;
import com.iteye.tianshi.core.web.service.BaseServiceImpl;
import com.iteye.tianshi.web.dao.base.TDictionaryDao;
import com.iteye.tianshi.web.dao.base.TDictionaryTypeDao;
import com.iteye.tianshi.web.model.base.TDictionary;
import com.iteye.tianshi.web.model.base.TDictionaryType;
import com.iteye.tianshi.web.service.base.TDictionaryService;

/**
 *
 * @datetime 2010-8-8 下午04:44:42
 * @author jiangzx@yahoo.com
 */
@Service
public class TDictionaryServiceImpl extends BaseServiceImpl<TDictionary, Long> implements TDictionaryService {
	//~ Instance fields ================================================================================================
	@Autowired
	private TDictionaryDao dictionaryDao;
	@Autowired
	private TDictionaryTypeDao dictionaryTypeDao;

	//~ Constructors ===================================================================================================
	
	//~ Methods ========================================================================================================
	@Override
	public GenericDao<TDictionary, Long> getGenericDao() {
		return this.dictionaryDao;
	}
	/**
	 * 根据字典业务类型，查询指定字典业务类型的所有子字典项
	 * 为了提高效率，增加缓存处理。
	 * 
	 * @param dictTypeId
	 * @return List<Dictionary>
	 */
	@Transactional(readOnly=true)
	public List<TDictionary> queryDictionarys(Long dictTypeId) {
		List<TDictionary> dictionaries = DictionaryHolder.getDictionaries(dictTypeId);
		if(dictionaries == null) {
			TDictionaryType dictionaryType = this.dictionaryTypeDao.find(dictTypeId);
			if(dictionaryType != null) {
				dictionaries = dictionaryType.getTDictionaries();
			}
			DictionaryHolder.putDictionaries(dictTypeId, dictionaries);
		}
		return dictionaries;
	}	
}
