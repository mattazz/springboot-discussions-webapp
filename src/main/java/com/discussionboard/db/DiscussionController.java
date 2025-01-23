package com.discussionboard.db;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private PostsRepository postsRepository;

//    Mappings for the dashboard and new-discussion pages API
    @GetMapping("/new-discussion")
    public String newDiscussionForm() {
        return "newDiscussion";
    }

    @GetMapping("/discussion/{id}")
    public ModelAndView viewDiscussion(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("discussion");
        Optional<Discussion> discussion = discussionRepository.findById(id);
        if (discussion.isPresent()) {
            modelAndView.addObject("discussion", discussion.get());
            List<Post> posts = postsRepository.findByDiscussionId(id);
            modelAndView.addObject("comments", posts);
        } else {
            modelAndView.setViewName("redirect:/dashboard");
            modelAndView.addObject("error", "Discussion not found");
        }
        return modelAndView;
    }

    @GetMapping("/new-post")
    public String newPostForm() {
        return "newPost";
    }

    @PostMapping("/new-post")
    public ModelAndView createPost(@RequestParam("content") String content,
                                   @RequestParam("discussionId") String discussionId,
                                   HttpServletRequest request) {
        String username = getUsernameFromSession(request);
        logger.info("Received new post request: content={}, discussionId={}, username={}", content, discussionId, username);
        ModelAndView modelAndView = new ModelAndView("newPost");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Post post = new Post();
            post.setContent(content);
            post.setAuthor(username); // Link the author to the username
            post.setDiscussionId(discussionId); // Set the discussionId
            postsRepository.save(post);
            logger.info("Post saved successfully: {}", post);
            modelAndView.setViewName("redirect:/discussion/" + discussionId);
        } else {
            logger.error("User not found: {}", username);
            modelAndView.addObject("error", "User not found");
        }
        return modelAndView;
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