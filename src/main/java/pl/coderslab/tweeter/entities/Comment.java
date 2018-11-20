package pl.coderslab.tweeter.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Entity
public class Comment {

    // == fields ==

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    @NotEmpty
    private String text;

    // == constructors ==

    public Comment() {
    }

    public Comment(User user, Tweet tweet, Calendar created, @NotEmpty String text) {
        this.user = user;
        this.tweet = tweet;
        this.created = created;
        this.text = text;
    }

    // == Getters and Setters

    public void setUser(User user) { this.user = user; }
    public void setTweet(Tweet tweet) { this.tweet = tweet; }
    public void setCreated(Calendar created) { this.created = created; }
    public void setText(String text) { this.text = text; }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Tweet getTweet() { return tweet; }
    public Calendar getCreated() { return created; }
    public String getText() { return text; }
}
