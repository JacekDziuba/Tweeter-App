package pl.coderslab.tweeter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.tweeter.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Tweet findTweetById(Long id);

}
