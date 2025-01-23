package com.discussionboard.db;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Create a new session
            String sessionId = UUID.randomUUID().toString();
            Session session = new Session(sessionId, username);
            sessionRepository.save(session);

            // Add session ID to cookies
            Cookie cookie = new Cookie("SESSIONID", sessionId);
            cookie.setPath("/");
            response.addCookie(cookie);

            modelAndView.setViewName("redirect:/dashboard");
        } else {
            modelAndView.addObject("error", "Invalid username or password");
        }
        return modelAndView;
    }
}