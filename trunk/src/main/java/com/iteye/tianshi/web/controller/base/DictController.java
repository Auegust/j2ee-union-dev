package com.iteye.tianshi.web.controller.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iteye.tianshi.core.page.Page;
import com.iteye.tianshi.core.page.PageRequest;
import com.iteye.tianshi.core.util.DictionaryHolder;
import com.iteye.tianshi.core.util.ResponseData;
import com.iteye.tianshi.core.web.controller.BaseController;
import com.iteye.tianshi.web.model.base.TDictionary;
import com.iteye.tianshi.web.model.base.TDictionaryType;
import com.iteye.tianshi.web.service.base.TDictionaryService;
import com.iteye.tianshi.web.service.base.TDictionaryTypeService;

/**
 * 业务字典
 * 
 * @datetime 2011-1-8 下午21:01:44
 * @author jiangzx@yahoo.com
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

	//~ Instance fields ================================================================================================
	@Autowired
	private TDictionaryTypeService dictService;
	@Autowired
	private TDictionaryService dictionaryService;
	
	//~ Methods ========================================================================================================
	@RequestMapping("/index")
	public String index(){
		return "admin/base/dict";
	}
	
	//----------------------------------------------字典类型---------------------------------------------------------
	
	/**
	 * 根据任何一种条件，分页查询出所有的字典类型
	 * 
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/pageQueryDictBusinTypes")
	@ResponseBody
	public Page<TDictionaryType> pageQueryDictBusinTypes(@RequestParam("start")int startIndex, 
			@RequestParam("limit")int pageSize, TDictionaryType businType, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PageRequest<TDictionaryType> pageRequest = new PageRequest<TDictionaryType>(startIndex, pageSize);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		
		Map<String, String> likeFilters = pageRequest.getLikeFilters();
		if(StringUtils.hasText(businType.getDictTypeName()))
			likeFilters.put("name", businType.getDictTypeName());
			
		return dictService.findAllForPage(pageRequest);
	}
	
	/**
	 * 新增一条字典类型
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/insertDictBusinType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertDictBusinType(TDictionaryType businType) {
		dictService.insertEntity(businType);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新某种字典类型
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/updateDictBusinType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateDictBusinType(TDictionaryType businType) {
		dictService.updateEntity(businType);
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询某种字典类型
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/loadDictBusinType", method=RequestMethod.POST)
	@ResponseBody
	public TDictionaryType loadDictBusinType(Long id) {
		return dictService.findEntity(id);
	}
	
	/**
	 * 删除一种字典类型
	 * 
	 * @param resource
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteDictBusinType", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteDictBusinType(Long id) {
		DictionaryHolder.cleanDictionaries(id);
		dictService.deleteEntity(id);
		return ResponseData.SUCCESS_NO_DATA;
	}

	//---------------------------------------字典项----------------------------------------------------------------
	
	/**
	 * 根据字典类型查询出所有字典项
	 * 
	 * @param dictTypeId 
	 */
	@RequestMapping("/queryDictionarys")
	@ResponseBody
	public List<TDictionary> queryDictionarys(Long dictTypeId) {
		return dictionaryService.queryDictionarys(dictTypeId);
	}
	
	/**
	 * 插入一条字典项
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value="/insertDictionary", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData insertDictionary(TDictionary dictionary) {
		dictionaryService.insertEntity(dictionary);
		
		DictionaryHolder.cleanDictionaries(dictionary.getTDictionaryType().getId());
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 更新一条字典项
	 * 
	 * @param dictionary
	 * @return
	 */
	@RequestMapping(value="/updateDictionary", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData updateDictionary(TDictionary dictionary) {
		dictionaryService.updateEntity(dictionary);
		
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 查询一条字典项
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadDictionary", method=RequestMethod.POST)
	@ResponseBody
	public TDictionary loadDictionary(Long id) {
		return dictionaryService.findEntity(id);
	}
	
	/**
	 * 删除一条字典项
	 * 
	 * @param id  
	 */
	@RequestMapping("/deleteDictionary")
	@ResponseBody
	public ResponseData deleteDictionary(Long id) {
		dictionaryService.deleteEntity(id);
		
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 删除多条字典项
	 * 
	 * @param ids id数组的形式传入
	 * @return ResponseData
	 */
	@RequestMapping(value="/deleteDictionarys", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData deleteEntityAttrs(Long[] ids) {
		for(Long id:ids){
			dictionaryService.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 *  根据字典类型‘分页’查询所有字典项
	 * 
	 * @param dictTypeId 字典类型ID
	 * @return
	 */
	@RequestMapping("/pageQueryDict")
	@ResponseBody
	public Page<TDictionary> pageQueryDict(@RequestParam("start")int startIndex, 
			@RequestParam("limit")int pageSize, Long dictTypeId, @RequestParam(required = false)String sort, 
			@RequestParam(required = false)String dir) {
		PageRequest<TDictionary> pageRequest = new PageRequest<TDictionary>(startIndex, pageSize);
		if(dictTypeId != null)
			pageRequest.getFilters().put("DICT_TYPE_ID", dictTypeId);
		
		if(StringUtils.hasText(sort) && StringUtils.hasText(dir))
			pageRequest.setSortColumns(sort + " " + dir);
		
		Page<TDictionary> page = dictionaryService.findAllForPage(pageRequest);
		return page;
	}
}
