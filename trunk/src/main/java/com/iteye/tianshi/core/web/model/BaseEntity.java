package com.iteye.tianshi.core.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @datetime 2010-8-12 下午05:15:55
 * @author jiangzx@yahoo.com
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//~ Instance fields ================================================================================================
	@Id
	@Column(name="res_id")
    @GeneratedValue(generator = "tableGenerator")     
	@GenericGenerator(name = "tableGenerator", strategy="increment")
	private Long id;
	
	//~ Methods ========================================================================================================
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
