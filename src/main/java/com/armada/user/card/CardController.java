package com.armada.user.card;

import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.annotation.SessionMap;

@ManagedBean
@SessionMap
public class CardController {
	private Map<String, Object> sessionMap;
	private CardDaoImpl cardDao;
	private List<CardPojo> cards;
	
	public List<CardPojo> loadCards(){
		setCards(cardDao.listCards());
		return cards;
		//setCard(cardModel.readAllCards);
	}
	public List<CardPojo> getCards(){
		return cards;
	}
	public void setCards(List<CardPojo> card) {
		this.cards= card;
	}
	public String navigateToAddCard() {
		sessionMap.put("newCard", new CardPojo());
		return "add-Card.xhtml?faces-redirect=true";
	}
	public String submitCard(CardPojo card) {
		cardDao.addCard(card);
		return "list-card.xhtml?faces-redirect=true";
	}
	public String navigateToAssignCard(int id) {
		CardPojo cardPojoObj = cardDao.readCardById(id);
		sessionMap.put("card", cardPojoObj);
		return "assign-card.xhtml?faces-redirect=true";
	}
	public String submitAssignCard(CardPojo cardPojo) {
		cardDao.assignCard(cardPojo);
		return "list-card.xhtml?faces-redirect=true";
	}
	public String navigateToUpdateCardType(int id) {
		CardPojo cardPojoObj = cardDao.readCardById(id);
		sessionMap.put("card", cardPojoObj);
		return "change-card-type.xhtml?faces-redirect=true";
	}
	public String submitCardType(CardPojo cardPojo) {
		cardDao.assignCard(cardPojo);
		return "list-card.xhtml?faces-redirect=true";
	}
	
	
//	public String navigateToAddIp() {
//		sessionMap.put("newCard", new CardPojo());
//		return "add-IP.xhtml?faces-redirect=true";
//	}
//	public String submitIp(CardPojo card) {
//		cardDao.addCard(card);
//		return "list-card.xhtml?faces-redirect=true";
//	}
	
}

