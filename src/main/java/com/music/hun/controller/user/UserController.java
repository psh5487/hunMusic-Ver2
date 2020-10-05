package com.music.hun.controller.user;

import com.music.hun.model.user.JoinRequest;
import com.music.hun.model.user.User;
import com.music.hun.security.JwtTokenProvider;
import com.music.hun.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/logInForm")
    public String logInForm() {
        return "form/logInForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "form/joinForm";
    }

    /* 회원가입 */
    @PostMapping("/join")
    public void join(@ModelAttribute JoinRequest joinRequest, HttpServletResponse response) throws IOException {
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
        alert(message, result, response);
    }

    // Alert 메시지 후, 리다이랙트
    void alert(String message, int result, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (result == 0) { // 성공한 경우
            String url = "http://localhost:8080/logInForm";
            out.println("<script>alert('" + message + "'); location.href='" + url + "';</script>");
        } else {
            out.println("<script>alert('" + message + "'); history.go(-1);</script>");
        }
        out.flush();
    }
}
