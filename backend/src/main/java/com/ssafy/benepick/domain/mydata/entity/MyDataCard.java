package com.ssafy.benepick.domain.mydata.entity;

import java.util.List;

import com.ssafy.benepick.domain.card.entity.CardCompany;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MyDataCard {

	@Id
	private String myDataCardId;

	@Column(nullable = false)
	private String myDataCardName;

	@Column(nullable = false)
	private String myDataCardExpirationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "my_data_user_id" , nullable = false)
	private MyDataUser myDataUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_company_id" , nullable = false)
	private CardCompany cardCompany;

	@OneToMany(mappedBy = "myDataCard", cascade = CascadeType.ALL , orphanRemoval = true)
	private List<MyDataPayment> myDataPaymentList;
}
