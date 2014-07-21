package it.pajak.blog.posts.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.pajak.blog.common.CustomDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

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

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime createDate;
}
