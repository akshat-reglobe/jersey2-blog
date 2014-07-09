package it.pajak.blog.posts.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
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

    @XmlTransient
    public final String id;

    @NotBlank
    @ApiModelProperty(required = true)
    public String title;

    public String slug;

    @NotBlank
    @ApiModelProperty(required = true)
    public String excerpt;

    @NotBlank
    @ApiModelProperty(required = true)
    public String content;

    @NotBlank
    @ApiModelProperty(required = true)
    public String type;

    @ApiModelProperty(required = true)
    public boolean isMore;

    public List<Comment> comments;

    public List<Image> images;

    private DateTime createDate;

    public Post() {
        this(null);
    }

    public Post(String id) {
        this.id = id;
    }
}
