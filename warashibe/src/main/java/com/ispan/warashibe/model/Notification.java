package com.ispan.warashibe.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "notificationID")

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notificationID")
	private Integer notificationID;
	
	@ManyToOne
	@JoinColumn(name = "receiverID")
	@JsonIdentityReference(alwaysAsId = true)
	private Members receiverID;
	
	@ManyToOne
	@JoinColumn(name = "senderID")
	@JsonIdentityReference(alwaysAsId = true)
	private Members senderID;
	
	@ManyToOne
	@JoinColumn(name = "orderID")
	@JsonIdentityReference(alwaysAsId = true)
	private Orders orderID;
	
	@Column(name = "content")
	private String content;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notifyDate")
	private Date notifyDate;
	
	@Column(name = "isRead")
	private Boolean isRead;
	
	@PrePersist
	public void onCreate() {
		if (notifyDate == null) {notifyDate = new Date();}
	}
	
}
