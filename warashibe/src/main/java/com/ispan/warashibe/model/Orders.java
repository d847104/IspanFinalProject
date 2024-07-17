package com.ispan.warashibe.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderID")

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderID")
	private Integer orderID;

	@ManyToOne
	@JoinColumn(name = "sellerID")
	private Members seller;

	@ManyToOne
	@JoinColumn(name = "buyerID")
	private Members buyer;

	@Column(name = "delivery", nullable = false, columnDefinition = "nvarchar(255)")
	private String delivery;

	@Column(name = "deliveryFee", nullable = false)
	private Integer deliveryFee;

	@Column(name = "payMethod", nullable = false, columnDefinition = "nvarchar(255)")
	private String payMethod;
	

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Column(name = "orderTime", nullable = false)
	private Date orderTime;

	@Column(name="orderStatus", nullable = false, columnDefinition = "nvarchar(255) default 'newOrder'")
	private String orderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Column(name = "orderUpdate")
	private Date orderUpdate;

	@Column(name = "isSecondHand", nullable = false, columnDefinition = "bit default 0")
	private boolean isSecondHand;
	
	@OneToMany(mappedBy = "order")
	@Column
	private Set<OrderProducts> orderProducts;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notificationID")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Notification> notificationID;
	
	@PrePersist
	public void onCreate() {
		if (orderTime == null) {orderTime = new Date();}
	}
	@PreUpdate
	public void onUpdate() {
		orderUpdate = new Date();
	}
}
