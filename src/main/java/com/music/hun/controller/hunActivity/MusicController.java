package com.music.hun.controller.hunActivity;

import com.music.hun.model.music.Music;
import com.music.hun.model.music.MusicCrawlingRequest;
import com.music.hun.model.music.MusicRequest;
import com.music.hun.service.music.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
public class MusicController {

    @Autowired
    MusicService musicService;

    /* 음반 리스트 */
    @GetMapping("/MusicList") // GET /MusicList?page=1&size=10&sort=registeredAt,desc
    public String musicList(Pageable pageable, Model model) {

        List<Music> musicList = musicService.findMusicsWithSize(pageable);
        Map<String, Integer> pageInfoMap = musicService.paginationInfo(pageable);

        model.addAttribute("list", musicList);
        model.addAttribute("pageRange", pageInfoMap.get("pageRange"));
        model.addAttribute("pageCount", pageInfoMap.get("pageCount"));
        model.addAttribute("cur", pageInfoMap.get("cur"));
        model.addAttribute("begin", pageInfoMap.get("begin"));
        model.addAttribute("lastBegin", pageInfoMap.get("lastBegin"));
        model.addAttribute("count", pageInfoMap.get("count"));

        return "musicList";
    }

    /* 음반 작성 Form */
    @GetMapping("/addMusicDirectly")
    public String addMusicDirectlyForm() {

        return "form/musicAddComplexForm";
    }

    @GetMapping("/addMusicSimply")
    public String addMusicSimplyForm() {

        return "form/musicAddSimpleForm";
    }

    @GetMapping("/updateMusic")
    public String updateMusicForm(@RequestParam("barcode") String barcode, Model model) {
        Music music = musicService.findMusicByBarcode(barcode);
        model.addAttribute("music", music);
        return "form/musicUpdateForm";
    }

    /* 음반 직접 추가 */
    @PostMapping("/addMusicDirectly")
    public void addMusicDirectly(@ModelAttribute MusicRequest musicRequest, HttpServletResponse response) throws IOException {
        int result = musicService.insertMusic(musicRequest, "insert");
        String message = "";

        if (result == 0) {
            message = "저장되었습니다!";
        } else if (result == -1) {
            message = "이미 저장된 음반입니다!";
        } else if (result == -2) {
            message = "필수 값을 입력하세요!";
        }

        // Alert 메시지 후, 리다이랙트
        alert(message, result, response);
    }

    /* 음반 간편 추가 */
    @PostMapping("/addMusicSimply")
    public void addMusicSimply(@ModelAttribute MusicCrawlingRequest musicCrawlingRequest,
                               HttpServletResponse response) throws IOException {
        int result = musicService.insetMusicSimply(musicCrawlingRequest);
        String message = "";

        if (result == 0) {
            message = "저장되었습니다!";
        } else if (result == -1) {
            message = "이미 저장된 음반입니다!";
        } else if (result == -2) {
            message = "음반 정보가 불충분하여, 직접 추가해야 합니다.";
        } else if (result == -3) {
            message = "현재 판매되지 않는 음반으로, 직접 추가해야 합니다.";
        }

        // Alert 메시지 후, 리다이랙트
        alert(message, result, response);
    }

    /* 음반 수정 */
    @PostMapping("/updateMusic")
    public void updateMusic(@ModelAttribute MusicRequest musicRequest, HttpServletResponse response) throws IOException {
        int result = musicService.updateMusic(musicRequest);
        String message = "";

        if (result == 0) {
            message = "수정되었습니다!";
        } else if (result == -2) {
            message = "필수 값을 입력하세요!";
        }

        // Alert 메시지 후, 리다이랙트
        alert(message, result, response);
    }

    /* 음반 삭제 */
    @PostMapping("/deleteMusic")
    public void deleteMusic(@RequestParam("item_barcode") String barcode,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {
        int result = musicService.deleteMusic(barcode);

        if (result == 0) {
            // alert 없이 페이지 이동(리다이랙트)
            String context = request.getContextPath();
            response.sendRedirect(context + "/MusicList?page=0&size=10&sort=registeredAt,desc");
        } else if (result == -3) {
            String message = "없는 음반입니다!";
            alert(message, result, response);
        }
    }

    // Alert 메시지 후, 리다이랙트
    void alert(String message, int result, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (result == 0) { // 성공한 경우
            String url = "http://localhost:8080/MusicList?page=0&size=10&sort=registeredAt,desc";
            out.println("<script>alert('" + message + "'); location.href='" + url + "';</script>");
        } else {
            out.println("<script>alert('" + message + "'); history.go(-1);</script>");
        }
        out.flush();
    }

}
