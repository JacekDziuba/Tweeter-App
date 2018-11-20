package pl.coderslab.tweeter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.tweeter.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTweetIdOrderByCreatedDesc(Long id);


}
