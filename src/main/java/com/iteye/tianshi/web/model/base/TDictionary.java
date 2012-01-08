package com.iteye.tianshi.web.model.base;

import java.io.Serializable;
import javax.persistence.*;


/**
 * 字典项
 * 
 */
@Entity
@Table(name="t_dictionary")
public class TDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="busi_id")
	private Long id;

	@Column(name="busi_name")
	private String busiName;

	@Column(name="busi_status")
	private String busiStatus;

	//bi-directional many-to-one association to TDictionaryType
    @ManyToOne
	@JoinColumn(name="dict_type_id")
	private TDictionaryType TDictionaryType;

    public TDictionary() {
    }

	public Long getBusiId() {
		return this.id;
	}

	public void setBusiId(Long id) {
		this.id = id;
	}

	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	public String getBusiStatus() {
		return this.busiStatus;
	}

	public void setBusiStatus(String busiStatus) {
		this.busiStatus = busiStatus;
	}

	public TDictionaryType getTDictionaryType() {
		return this.TDictionaryType;
	}

	public void setTDictionaryType(TDictionaryType TDictionaryType) {
		this.TDictionaryType = TDictionaryType;
	}
	
}