package com.kicinger.hsbc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Entity
@Table(name = "FOLLOWING")
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_FOLLOWEE",
            foreignKey = @ForeignKey(name = "FOLLOWING_FOLLOWEE_FK"),
            nullable = false)
    private User followee;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_FOLLOWER",
            foreignKey = @ForeignKey(name = "FOLLOWING_FOLLOWER_FK"),
            nullable = false)
    private User follower;

    public Following() {
    }

    private Following(User followee, User follower) {
        this.followee = followee;
        this.follower = follower;
    }

    public Long getId() {
        return id;
    }

    public User getFollowee() {
        return followee;
    }

    public User getFollower() {
        return follower;
    }

    public static Following create(User followee, User follower) {
        return new Following(followee, follower);
    }
}
