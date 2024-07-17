package com.ispan.warashibe.model;

import java.util.Date;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderID")	// 處理JACKSON循環引用
@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert

public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderID")
	private Integer orderID;
	
	@ManyToOne
	@JoinColumn(name = "sellerID")
	@JsonIdentityReference(alwaysAsId = true)	//查詢 Orders 時僅引用 MemberID 而非整個 Member Object
	private Members seller;
	
	@ManyToOne
	@JoinColumn(name = "buyerID")
	@JsonIdentityReference(alwaysAsId = true)	//查詢 Orders 時僅引用 MemberID 而非整個 Member Object
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
	@Column(name = "orderUpdate", columnDefinition = "datetime2 default ''")
	private Date orderUpdate;

	@Column(name = "isSecondHand", nullable = false, columnDefinition = "bit default 0")
	private boolean isSecondHand;
	
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
