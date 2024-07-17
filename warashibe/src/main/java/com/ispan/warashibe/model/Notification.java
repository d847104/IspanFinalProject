package com.ispan.warashibe.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@JsonIgnore   //防止無限遞歸
	private Members receiverID;
	
	@ManyToOne
	@JoinColumn(name = "senderID")
	@JsonIgnore   //防止無限遞歸
	private Members senderID;
	
	@ManyToOne
	@JoinColumn(name = "orderID")
	@JsonIgnore   //防止無限遞歸
	private Orders orderID;
	
	@Column(name = "content")
	private String content;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notifyDate")
	private Date notifyDate;
	
	@Column(name = "isRead")
	private Boolean isRead;
}
