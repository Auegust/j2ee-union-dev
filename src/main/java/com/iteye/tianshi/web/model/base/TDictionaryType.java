package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


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

	@OneToMany(mappedBy="TDictionaryType")
	private Set<TDictionary> TDictionaries;

    public TDictionaryType() {
    }

	public String getDictTypeName() {
		return this.dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public Set<TDictionary> getTDictionaries() {
		return this.TDictionaries;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTDictionaries(Set<TDictionary> TDictionaries) {
		this.TDictionaries = TDictionaries;
	}
	
}