package com.discussionboard.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Main controller for api requests for the dashboard
@Controller
public class HomeController {

    @Autowired
    private DiscussionRepository discussionRepository;

    @GetMapping("/")
        public String home() {
            return "index";
        }

//     Lists all discussions on the dashboard
    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Discussion> discussions = discussionRepository.findAll();
        model.addAttribute("discussions", discussions);
        return "dashboard";
    }
}
