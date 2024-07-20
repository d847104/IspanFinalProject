package com.ispan.warashibe.model;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonIdentityReference(alwaysAsId = true)
	private Members senderID;

	@JsonProperty("senderID")
	public void setSenderID(Integer memberID) {
		this.senderID = new Members();
		this.senderID.setMemberID(memberID);
	}

	@JsonProperty("senderID")
	public Integer getSenderID() {
		return (this.senderID != null) ? this.senderID.getMemberID() : null;
	}

	@ManyToOne
	@JoinColumn(name = "receiverID")
	@JsonIdentityReference(alwaysAsId = true)
	private Members receiverID;

	@JsonProperty("receiverID")
	public void setReceiverID(Integer memberID) {
		this.receiverID = new Members();
		this.receiverID.setMemberID(memberID);
	}
	
	@JsonProperty("receiverID")
	public Integer getReceiverID() {
		return (this.receiverID != null) ? this.receiverID.getMemberID() : null;
	}

	@Column(name = "msg")
	private String msg;

	@Column(name = "msgTime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date msgTime;

//	@Override
//	public String toString() {
//		return "model.Messenger["+ msgID +"," + senderID +"," + receiverID +","+ msg +","+ msgTime +"]";
//	}

}
