package com.ispan.warashibe.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FavoriteID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer memberID;
	private Integer productID;
	
	public FavoriteID(Integer memberID, Integer productID) {
        this.memberID = memberID;
        this.productID = productID;
    }
}
