package it.pajak.scry.cards.repository;

import it.pajak.scry.cards.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends MongoRepository<Card, String> {

    public Card findOneByName(String name);
}
