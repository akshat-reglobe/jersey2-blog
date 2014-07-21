package it.pajak.blog.posts.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import it.pajak.blog.common.CustomDateSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Document(collection = "posts")
@ApiModel(value = "Blog posts")
@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Post {

    public String id;

    @NotBlank
    @ApiModelProperty(required = true)
    public String title;

    @NotBlank
    @ApiModelProperty(required = true)
    public String content;

    @NotBlank
    @ApiModelProperty(required = true)
    public String author;

    public String slug;

    @NotBlank
    @ApiModelProperty(required = true)
    public String excerpt;

    public List<String> tags;

    public String demoUrl;

    public String repositoryUrl;

    @NotBlank
    @ApiModelProperty(required = true)
    public String type;

    @NotBlank
    @ApiModelProperty(required = true)
    public String section;

    @ApiModelProperty(required = true)
    public boolean extended;

    @ApiModelProperty(required = true)
    public boolean visible;

    public List<Comment> comments;

    public List<Image> images;

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime createDate;
}
