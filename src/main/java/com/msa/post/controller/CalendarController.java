package com.msa.post.controller;

import com.msa.member.domain.Member;
import com.msa.post.domain.Post;
import com.msa.post.dto.PostDto;
import com.msa.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;


@Controller
public class CalendarController {

    // Assuming you have a service to handle post retrieval
    private final PostService postService;

    // Constructor for controller
    public CalendarController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/signUp")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberDto", new Member("username","email","password")); // A
        return "signUp"; // This should be the name of the Thymeleaf template that contains the form
    }
    @GetMapping("/addPost")
    public String showAddPostForm(Model model) {
        model.addAttribute("postDto", new PostDto("제목","내용")); // A
        return "addPost"; // This should be the name of the Thymeleaf template that contains the form
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    //이슈 고치기..
    @GetMapping({"/", "/index"})
    public String calendar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            // User not logged in
            return "redirect:/login";
        } else {
            // User logged in
            return "redirect:/home";
        }
        /*Map<LocalDate, List<Post>> postsMap = new HashMap<>();

        // 이번달 전체 날짜의 post들을 가져온다.
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        List<Post> posts = postService.getPostsByDateRange(start, end);

        for (Post post : posts) {
            LocalDate date = post.getDate(); // Assuming Post has a getDate() method
            postsMap.computeIfAbsent(date, k -> new ArrayList<>()).add(post);
        }

        model.addAttribute("postsMap", postsMap);
        Map<String, List<Integer>> calendarData = CalendarUtils.generateCalendarData();
        model.addAttribute("calendarData", calendarData);
        return "index";*/
    }

    //한 날짜의 글만 가져오도록 수정하기. 이 페이지(daily_posts.html) 없애기.
    @GetMapping("/index/{date}")
    public String dailyPosts(@PathVariable String date, Model model) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            List<Post> posts = postService.getPostsByDate(parsedDate);
            model.addAttribute("posts", posts);
            model.addAttribute("date", date);
        } catch (Exception e) {
            // Handle parsing error or no posts found
            model.addAttribute("error", "Invalid date or no posts available.");
        }
        return "daily_posts"; // Template showing posts for the selected date
    }
}
