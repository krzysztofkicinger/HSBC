package com.kicinger.hsbc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nick;

    @JsonIgnore
    @JsonProperty(value = "postedMessages")
    @OneToMany(mappedBy = "author")
    private Set<Message> postedMessages;

    @JsonIgnore
    @JsonProperty(value = "followees")
    @OneToMany(mappedBy = "follower")
    private Set<Following> followees;

    public User() {
    }

    private User(String nick) {
        this.nick = nick;
        this.postedMessages = new HashSet<>();
        this.followees = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public Set<Message> getPostedMessages() {
        return postedMessages;
    }

    public Set<Following> getFollowees() {
        return followees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return nick != null ? nick.equals(user.nick) : user.nick == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nick != null ? nick.hashCode() : 0);
        return result;
    }

    public static User create(String nick) {
        return new User(nick);
    }

}
