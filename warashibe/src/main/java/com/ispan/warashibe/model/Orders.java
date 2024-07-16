package com.ispan.warashibe.model;

import java.util.Date;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderID")
	private Integer orderID;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "sellerID")
	private Members seller;

	@JsonManagedReference
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

	@Column(name = "orderStatus", nullable = false, columnDefinition = "nvarchar(255) default 'newOrder'")
	private String orderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Column(name = "orderUpdate")
	private Date orderUpdate;

	@Column(name = "isSecondHand", nullable = false, columnDefinition = "bit default 0")
	private boolean isSecondHand;

	@JsonBackReference
	@OneToMany(mappedBy = "order")
	private List<OrderProducts> orderProducts;

	@PrePersist
	public void onCreate() {
		if (orderTime == null) {orderTime = new Date();}
	}

	@PreUpdate
	public void onUpdate() {orderUpdate = new Date();}
	
	@JsonProperty("seller")	// 使用 JACKSON 反序列化 JSON 字串為 Order 物件時,需處理 seller 資料型態為 member 而非 int
    public void setSellerById(Integer sellerId) {
        this.seller = new Members();
        this.seller.setMemberID(sellerId);
    }

    @JsonProperty("buyer")	// 使用 JACKSON 反序列化 JSON 字串為 Order 物件時,需處理 buyer 資料型態為 member 而非 int
    public void setBuyerById(Integer buyerId) {
        this.buyer = new Members();
        this.buyer.setMemberID(buyerId);
    }
}
