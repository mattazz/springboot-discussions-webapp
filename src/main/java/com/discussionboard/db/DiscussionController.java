package com.discussionboard.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@Controller
public class DiscussionController {

    private static final Logger logger = LoggerFactory.getLogger(DiscussionController.class);

//    @Autowired annotation to inject the DiscussionRepository, UserRepository, and SessionRepository
    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

//    Mappings for the dashboard and new-discussion pages API
    @GetMapping("/new-discussion")
    public String newDiscussionForm() {
        return "newDiscussion";
    }

    @PostMapping("/new-discussion")
    public ModelAndView createDiscussion(@RequestParam("title") String title,
                                         @RequestParam("content") String content,
                                         HttpServletRequest request) {
        String username = getUsernameFromSession(request);
        logger.info("Received new discussion request: title={}, content={}, username={}", title, content, username);
        ModelAndView modelAndView = new ModelAndView("newDiscussion");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Discussion discussion = new Discussion();
            discussion.setTitle(title);
            discussion.setContent(content);
            discussion.setAuthor(username); // Link the author to the username
            discussionRepository.save(discussion);
            logger.info("Discussion saved successfully: {}", discussion);
            modelAndView.setViewName("redirect:/dashboard");
        } else {
            logger.error("User not found: {}", username);
            modelAndView.addObject("error", "User not found");
        }
        return modelAndView;
    }
//    Function to get the username from the session, used to link the author to the username
    private String getUsernameFromSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    Optional<Session> session = sessionRepository.findById(cookie.getValue());
                    if (session.isPresent()) {
                        return session.get().getUsername();
                    }
                }
            }
        }
        return null;
    }
}