package com.music.hun.controller.user;

import com.music.hun.model.user.JoinRequest;
import com.music.hun.model.user.User;
import com.music.hun.service.aws.S3Service;
import com.music.hun.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    @GetMapping("/logInForm")
    public String logInForm() {
        return "form/logInForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "form/joinForm";
    }

    @GetMapping("/myPageForm")
    public String myPageForm() {
        return "form/myPageForm";
    }

    /* 회원가입 */
    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequest joinRequest, Model model) {
        int result = userService.registerMember(joinRequest.getEmail(), joinRequest.getName(), joinRequest.getPassword());

        String message = "";

        if (result == 0) {
            message = "가입되었습니다!";
        } else if (result == -1) {
            message = "이미 가입된 이메일입니다!";
        } else if (result == -2) {
            message = "필수 값을 입력하세요!";
        }

        // Alert 메시지 후, 리다이랙트
        model.addAttribute("message", message);
        model.addAttribute("url", "/logInForm");
        return "redirect";
    }

    /* 프로필 사진 등록 */
    @PostMapping("/registerProfile")
    public String registerProfile(@RequestParam("user_id") Long userId, MultipartFile file) throws IOException { //@AuthenticationPrincipal User user
        String imgPath = s3Service.upload(file);
        User updatedUser = userService.registerProfile(userId, imgPath); //user.getId()

        return "redirect:/myPageForm";
    }
}
