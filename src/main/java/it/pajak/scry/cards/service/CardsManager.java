package it.pajak.scry.cards.service;

import it.pajak.scry.cards.model.Card;
import it.pajak.scry.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardsManager {

    @Autowired
    private CardsRepository cardsRepository;

    public void create(Card card) {
        cardsRepository.save(card);
    }

    public void update(Card card) {
        cardsRepository.save(card);
    }

    public void delete(Card card) {
        cardsRepository.delete(card);
    }

    public void delete(String cardId) {
        cardsRepository.delete(cardId);
    }

    public Card getById(String cardId) {
        return cardsRepository.findOne(cardId);
    }

    public Card getByName(String cardName) {
        return cardsRepository.findOneByName(cardName);
    }
}
