package com.music.controller.userActivity;

import com.music.model.user.User;
import com.music.model.userActivity.Likes;
import com.music.service.userActivity.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class LikesRestController {

    private final LikesService likesService;

    /* 인증된 사용자의 좋아요 처리 */
    @PostMapping("likes/{musicId}")
    public ResponseEntity likesMusic(@AuthenticationPrincipal User user,
                                     @PathVariable Long musicId) {
        if (user == null) {
            return new ResponseEntity<>("Not Logged in User", HttpStatus.UNAUTHORIZED);
        }

        Likes result = likesService.likeOrUnlike(user.getId(), musicId);

        String action = "";
        if (result == null) { // Unlike 처리 된 경우
            action = "Unlike";
        } else { // like 처리 된 경우
            action = "Like";
        }

        LikesDto likesDto = LikesDto.builder()
                .action(action)
                .username(user.getName())
                .musicId(musicId)
                .createdAt(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(likesDto, HttpStatus.OK);
    }

}
