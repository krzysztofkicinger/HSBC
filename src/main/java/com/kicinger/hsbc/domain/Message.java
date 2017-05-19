package com.kicinger.hsbc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 140)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss"
    )
    private Date createdOn;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_AUTHOR",
            foreignKey = @ForeignKey(name = "MESSAGE_AUTHOR_FK"))
    private User author;

    public Message() {
    }

    private Message(String content, User author) {
        this.content = content;
        this.createdOn = new Date();
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null) return false;
        if (createdOn != null ? !createdOn.equals(message.createdOn) : message.createdOn != null) return false;
        return author != null ? author.equals(message.author) : message.author == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    public static Message create(String content, User author) {
        return new Message(content, author);
    }

}
