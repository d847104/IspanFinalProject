package com.ispan.eeit184.model;

import java.sql.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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
@Table(name="Messenger")
public class Messenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="msgID")
	private Integer msgID;
	
//	@Column(name="senderID")
	@ManyToOne
	@MapsId("senderID")
	private Integer senderID;
	
//	@Column(name="receiverID")
	@OneToMany
	@MapsId("receiverID")
	private Integer receiverID;
	
	@Column(name="msg")
	private String msg;
	
	@Column(name="msgTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date msgTime;
}
