package it.pajak.blog.posts.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Comment {

    @NotBlank
    public String author;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String comment;
}
