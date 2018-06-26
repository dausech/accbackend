package me.dausech.accbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String accountNumber;

	@Column
	private String description;

	@Column
	private AccountType accountType;

}
