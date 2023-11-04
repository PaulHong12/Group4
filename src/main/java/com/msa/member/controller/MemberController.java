package com.msa.member.controller;

import com.msa.auth.TokenInfo;
import com.msa.member.domain.Member;
import com.msa.member.dto.LoginDto;
import com.msa.member.dto.SignupDto;
import com.msa.member.dto.UserDto;
import com.msa.member.service.MemberService;
import com.msa.post.dto.PostDto;
import com.msa.post.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{userId}")
    public ResultDto<List<UserDto>> getFriendList(@PathVariable("userId") long userId){
        
        return null;
    }

    @PostMapping("/auth/signUp")
    public ResponseEntity<ResultDto<Member>> signUp(@RequestBody SignupDto signupDto) {
        Member newMember = memberService.addUser(signupDto.userName(), signupDto.email(), signupDto.password());
        return ResponseEntity.ok()
                .body(new ResultDto<>(200, "", newMember));
    }

    @PostMapping(value = "/auth/login",
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultDto<TokenInfo>> login(@RequestBody LoginDto loginDto) {

        TokenInfo tokenInfo = memberService.login(loginDto.email(), loginDto.password());

        return ResponseEntity.ok()
                .header(SET_COOKIE,  generateCookie("accessToken", tokenInfo.accessToken()).toString())
                .header(SET_COOKIE,  generateCookie("refreshToken", tokenInfo.refreshToken()).toString())
                .body(new ResultDto<>(200, "", tokenInfo));
    }

    private ResponseCookie generateCookie(String from, String token) {
        return ResponseCookie.from(from, token)
                .httpOnly(true)
                .path("/")
                .build();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ResultDto<String>> logout(HttpServletRequest request, HttpServletResponse response) {

        memberService.logout(request, response);

        return ResponseEntity.ok()
                .body(new ResultDto<>(200, "Logged out successfully", ""));
    }


}
