package pl.coderslab.tweeter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.tweeter.Repositories.CommentRepository;
import pl.coderslab.tweeter.Repositories.TweetRepository;
import pl.coderslab.tweeter.Repositories.UserRepository;
import pl.coderslab.tweeter.entities.Comment;
import pl.coderslab.tweeter.entities.Tweet;
import pl.coderslab.tweeter.entities.User;
import pl.coderslab.tweeter.util.AttributeNames;
import pl.coderslab.tweeter.util.Mappings;
import pl.coderslab.tweeter.util.ViewNames;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class TweeterController {

    // == repositories ==

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private CommentRepository commentRepository;

    // == url handlers ==

    @GetMapping(Mappings.SHOW_TWEET_DETAILS)
    public String showMoreDetails(@PathVariable Long id,
                                  Model model) {

        Tweet tweet = tweetRepository.findTweetById(id);
        model.addAttribute(AttributeNames.CURRENT_TWEET, tweet);

        //adding_comments
        List<Comment> comments = commentRepository.findAllByTweetIdOrderByCreatedDesc(tweet.getId());
        model.addAttribute(AttributeNames.COMMENTS, comments);

        //adding_newComment_form
        Comment comment = new Comment();
        model.addAttribute(AttributeNames.NEW_COMMENT, comment);

        return ViewNames.MORE_DETAILS;
    }

    @PostMapping(Mappings.SHOW_TWEET_DETAILS)
    public String receiveMoreDetails(@PathVariable Long id,
                                     @Valid Comment newComment,
                                     BindingResult result,
                                     HttpSession session) {
        if(result.hasErrors()) {
            return ViewNames.MORE_DETAILS;
        }

        User user = (User) session.getAttribute(AttributeNames.USER);
        newComment.setUser(user);

        Tweet tweet = tweetRepository.findTweetById(id);
        newComment.setTweet(tweet);

        commentRepository.save(newComment);
        return "redirect:/showTweetDetails/" + id;
    }

    // == model attributes ==

    @ModelAttribute("users")
    public List<User> getAuthorList() {
        return userRepository.findAll();
    }
}
