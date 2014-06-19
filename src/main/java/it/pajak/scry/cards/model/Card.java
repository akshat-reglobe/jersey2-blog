package it.pajak.scry.cards.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Document(collection = "cards")
@XmlRootElement
public class Card {

    @Id
    @XmlTransient
    public String id;

    @NotBlank
    public String name;

    public String cost;

    public String power;

    public String toughness;

    public String slug;

    public List<String> types;

    public List<String> subtypes;

    public List<String> rules;

    public List<Appearance> appearance;
}
