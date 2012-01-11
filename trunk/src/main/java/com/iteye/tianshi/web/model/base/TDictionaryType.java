package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 字典类型
 * 
 */
@Entity
@Table(name="t_dictionary_type")
public class TDictionaryType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dict_type_id")
	private Long id;

	@Column(name="dict_type_name")
	private String dictTypeName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="TDictionaryType", fetch = FetchType.LAZY)
	private List<TDictionary> TDictionaries;

    public TDictionaryType() {
    }

	public String getDictTypeName() {
		return this.dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public List<TDictionary> getTDictionaries() {
		return this.TDictionaries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTDictionaries(List<TDictionary> TDictionaries) {
		this.TDictionaries = TDictionaries;
	}
	
}