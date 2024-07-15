package com.ispan.warashibe.model;


import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
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
@Table(name = "Messenger")
public class Messenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "msgID")
	private Integer msgID;

	@ManyToOne
	@JoinColumn(name = "senderID")
	private Members senderID;

	@ManyToOne
	@JoinColumn(name = "receiverID")
	private Members receiverID;

	@Column(name = "msg")
	private String msg;

	@Column(name = "msgTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date msgTime;
}
