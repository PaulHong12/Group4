package com.msa.friend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/member")
@RestController
public class FriendController {
    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    //친구 목록 가져오기
    @GetMapping("/{memberId}")
    public ResultDto<List<FriendDto>> getFriendList(@PathVariable("memberId") long memberId) {
        List<FriendDto> friendDtos = friendService.getFriendList(memberId)
                .stream()
                .map(Friend::convert2DTO)
                .toList();

        return new ResultDto<>(200, "ok", friendDtos);
    }

    //친구 검색
    @GetMapping("/{friendId}")
    public ResultDto<FriendDto> getFriend(@PathVariable("friendId") long friendId) {
        Optional<FriendDto> optFriend = friendService.getFriend(friendId)
                .map(Friend::convert2DTO);

        if (optFriend.isEmpty()) {
            throw new IllegalArgumentException("not exist friend : %s".formatted(friendId));
        } else {
            return new ResultDto<>(200, "ok", optFriend.get());
        }
    }

    //친구 추가
    @PutMapping("/{memberId}/{friendId}")
    public ResultDto addFriend(@PathVariable("memberId") long memberId, @PathVariable("friendId") long friendId) {
        friendService.getFriend(friendId)
                .ifPresentOrElse(post -> friendService.addFriend(memberId, friendId),
                        () -> {
                            throw new IllegalArgumentException("not exist friend : %s".formatted(friendId));
                        });

        return new ResultDto<>(200, "ok", null);
    }

    //친구 삭제
    @DeleteMapping("/{memberId}/{friendId}")
    public ResultDto deleteFriend(@PathVariable("memberId") long memberId, @PathVariable("friendId") long friendId) {
        friendService.getFriend(friendId)
                .ifPresentOrElse(post -> friendService.deleteFriend(memberId, friendId),
                        () -> {
                            throw new IllegalArgumentException("not exist friend : %s".formatted(friendId));
                        });

        return new ResultDto<>(200, "ok", null);
    }
}
