package com.msa.post.controller;

import com.msa.member.domain.Member;
import com.msa.member.service.MemberService;
import com.msa.post.domain.Post;
import com.msa.post.dto.PostDto;
import com.msa.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CalendarController {

    // Assuming you have a service to handle post retrieval
    private final PostService postService;
    private final MemberService memberService;

    // Constructor for controller
    public CalendarController(PostService postService, MemberService memberService, PostController postController) {
        this.postService = postService;
        this.memberService = memberService;
    }
    @GetMapping("/signUp")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberDto", new Member("username","email","password")); // A
        return "signUp"; // This should be the name of the Thymeleaf template that contains the form
    }
    @GetMapping("/addPost")
    public String showAddPostForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            //뒤에-user이 붙음..
            currentUsername = authentication.getName(); // Gets the username of the currently logged-in user
        }

        PostDto postDto =  new PostDto("제목", "내용", currentUsername);
        postDto.setUsername(currentUsername); // Set the username in PostDto

        model.addAttribute("postDto", postDto);
        return "addPost";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
/*
    @GetMapping("/{username}/home")
    public String home(@PathVariable String username, Model model, HttpServletRequest request) {
    Map<LocalDate, List<Post>> postsMap = new HashMap<>();
    //why is profileUser null?
        Member profileUser = memberService.findByUsername(username);
        Member loggedInUser = memberService.getLoggedInUser(request); // Implement this method to identify the logged-in user
        boolean isFriend = memberService.isFriend(profileUser.getId(), loggedInUser.getId()); // Implement this method to check friendship

        // 이번달 전체 날짜의 post들을 가져온다
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        List<Post> posts;
        if (profileUser.getUsername().equals(loggedInUser.getUsername()) || isFriend) {
            posts = postService.getPostsByDateRangeAndMember(start, end, username);
        } else {
            //redirect to "recommendedFriends";
            String loggInUsername = loggedInUser.getUsername();
            return "redirect:/"+ loggInUsername +"/home";

        }
       // List<Post> posts = postService.getPostsByDateRangeAndMember(start, end, username);

        for (Post post : posts) {
            LocalDate date = post.getDate(); // Assuming Post has a getDate() method
            postsMap.computeIfAbsent(date, k -> new ArrayList<>()).add(post);
        }

        model.addAttribute("postsMap", postsMap);
        List<List<LocalDate>> weeks = CalendarUtils.generateCalendarData();
        model.addAttribute("weeks", weeks);
        return "home";
    }
*/


    //메인화면. 캘린더 API와 합치기 시도중
@GetMapping("/{username}/home")
public String home(@PathVariable String username, Model model, HttpServletRequest request) {
    // 날짜별로 글 리스트 저장하는 HashMap
    Map<LocalDate, List<Post>> postsMap = new HashMap<>();
    Member profileUser = memberService.findByUsername(username);
    Member loggedInUser = memberService.getLoggedInUser(request); // Implement this method to identify the logged-in user
    boolean isFriend = memberService.isFriend(profileUser.getId(), loggedInUser.getId()); // Implement this method to check friendship

    if (!profileUser.getUsername().equals(loggedInUser.getUsername()) || !isFriend) {
        // Redirect if not the owner or a friend
        String loggedInUsername = loggedInUser.getUsername();
        return "redirect:/" + loggedInUsername + "/home";
    }

    // Initialize days array
    LocalDate start = LocalDate.now().withDayOfMonth(1);
    LocalDate end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    List<Post> posts = postService.getPostsByDateRangeAndMember(start, end, username);
    int[] days = new int[end.getDayOfMonth()];

    // Update days array based on posts
    for (Post post : posts) {
        LocalDate date = post.getDate();
        if (date.getMonth().equals(LocalDate.now().getMonth())) {
            days[date.getDayOfMonth() - 1] = 1;
        }
    }
    // Add to model
    model.addAttribute("days", days);

    return "home";
}

    @GetMapping({"/", "/index"})
    public String calendar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            // User not logged in
            return "redirect:/login";
        } else {
            // change to /{username}/home
            return "redirect:/home";
        }
    }

        //특정 날짜 글 조회
        @GetMapping("/home/{username}/{date}")
        public String dailyPosts(@PathVariable String username, @PathVariable String date, Model model) {
        try {
            // Define a DateTimeFormatter to specify the input date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            List<Post> posts = postService.getPostsByDateRangeAndMember(localDate, localDate, username);
            model.addAttribute("posts", posts);
            model.addAttribute("date", date);
        } catch (Exception e) {
            // Handle parsing error or no posts found
            model.addAttribute("error", "Invalid date or no posts available.");
        }
        return "daily_posts"; // Template showing posts for the selected date
    }

    @GetMapping("/editPost/{postId}")
    public String showEditPostForm(@PathVariable long postId, Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            //뒤에-user이 붙음..
            currentUsername = authentication.getName(); // Gets the username of the currently logged-in user
        }

        PostDto postDto =  new PostDto("제목", "내용", currentUsername, postId);
        postDto.setUsername(currentUsername); // Set the username in PostDto

        model.addAttribute("postDto", postDto);
        return "editPost";
    }


}
