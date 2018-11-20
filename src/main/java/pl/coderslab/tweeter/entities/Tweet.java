package pl.coderslab.tweeter.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Data
@Entity(name = "tweets")
public class Tweet {

    // == fields ==

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 140)
    private String text;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // == constructors ==

    public Tweet() {
    }

    public Tweet(@NotEmpty String text, Calendar created, User user) {
        this.text = text;
        this.created = created;
        this.user = user;
    }

}
