package com.armada.user.card;

import java.util.List;

public interface CardDao {

	public List<CardPojo> listCards();

	CardPojo addCard(CardPojo card);

	public void assignCard(CardPojo card);

	void banCard(int cardNumber);

	void changeCardType(CardPojo card);

	CardPojo readCardById(int cardNumber);

}
