package pl.coderslab.tweeter.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.tweeter.Repositories.TweetRepository;
import pl.coderslab.tweeter.Repositories.UserRepository;
import pl.coderslab.tweeter.entities.Tweet;
import pl.coderslab.tweeter.entities.User;
import pl.coderslab.tweeter.entities.UserCredential;
import pl.coderslab.tweeter.util.AttributeNames;
import pl.coderslab.tweeter.util.Mappings;
import pl.coderslab.tweeter.util.ViewNames;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    // == repositories ==

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    // == url handlers ==

    @GetMapping(Mappings.REGISTER)
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute(AttributeNames.USER, user);
        return ViewNames.REGISTER;
    }

    @PostMapping(Mappings.REGISTER)
    public String registerNewUser(@Valid User user,
                                  BindingResult result,
                                  HttpSession session) {

        if (result.hasErrors()) {
            return Mappings.REGISTER;
        }

        User newUser = userRepository.findUserByEmail(user.getEmail());
        if (newUser != null) {
            session.setAttribute(AttributeNames.MESSAGE, AttributeNames.USER_ALREADY_REGISTERED_MESSAGE);
            return ViewNames.HOME;
        }
        userRepository.save(user);
        session.setAttribute(AttributeNames.MESSAGE, AttributeNames.REGISTRATION_SUCCESSFUL_MESSAGE);
        return ViewNames.HOME;
    }

    @GetMapping(Mappings.LOGIN)
    public String showLoginForm(Model model) {
        UserCredential userCredential = new UserCredential();
        model.addAttribute(AttributeNames.USER_CREDENTIAL, userCredential);
        return ViewNames.LOGIN;
    }

    @PostMapping(Mappings.LOGIN)
    public String validateLoginForm(@Valid UserCredential userCredential,
                                    BindingResult result,
                                    HttpSession session) {

        if (result.hasErrors()) {
            return Mappings.LOGIN;
        }

        User savedUser = userRepository.findUserByEmail(userCredential.getEmail());
        if (BCrypt.checkpw(userCredential.getPassword(), savedUser.getPassword())) {
            session.setAttribute(AttributeNames.USER, savedUser);
            return Mappings.REDIRECT_HOMEPAGE;
        }

        session.setAttribute(AttributeNames.MESSAGE, AttributeNames.LOGIN_FAILED_MESSAGE);
        return ViewNames.HOME;
    }

    @GetMapping(Mappings.HOME_PAGE)
    public String getHomePage(Model model) {

        List<Tweet> tweets = tweetRepository.findAll();
        model.addAttribute(AttributeNames.TWEETS, tweets);

        Tweet newTweet = new Tweet();
        model.addAttribute(AttributeNames.NEWTWEET, newTweet);
        return ViewNames.HOME_PAGE;
    }

    @PostMapping(Mappings.HOME_PAGE)
    public String showHomePage(@Valid Tweet tweet,
                               BindingResult result,
                               HttpSession session) {

        if (result.hasErrors()) {
            return Mappings.HOME_PAGE;
        }

        User user = (User) session.getAttribute(AttributeNames.USER);
        tweet.setUser(user);
        tweetRepository.save(tweet);
        return Mappings.REDIRECT_HOMEPAGE;
    }

}
