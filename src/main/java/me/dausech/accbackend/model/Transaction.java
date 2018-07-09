package me.dausech.accbackend.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Transaction {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne
	private Account debitAccount;
	
	@ManyToOne
	private Account creditAccount;
	
	@Column(scale=2)
	private BigDecimal value;
	
}
