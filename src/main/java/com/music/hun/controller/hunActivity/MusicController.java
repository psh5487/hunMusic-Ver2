package com.music.hun.controller.hunActivity;

import com.music.hun.model.music.Music;
import com.music.hun.model.music.MusicCrawlingRequest;
import com.music.hun.model.music.MusicRequest;
import com.music.hun.service.music.MusicChooseService;
import com.music.hun.service.music.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    MusicChooseService musicChooseService;

    /* 음반 리스트 */
    @GetMapping("/MusicList") // GET /MusicList?page=1&size=10&sort=registeredAt,desc
    public String musicList(@PageableDefault(sort="registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {

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

    /* 이번 주 감상곡 표 페이지 */
    @GetMapping("/thisWeekMusic")
    public String thisWeekMusic() {
        return "thisWeekMusic";
    }

    /* 음반 직접 추가 */
    @PostMapping("/addMusicDirectly")
    public String addMusicDirectly(@ModelAttribute MusicRequest musicRequest, Model model) {
        int result = musicService.insertMusic(musicRequest, "insert");
        String message = "";
        String url = "/MusicList";

        if (result == 0) {
            message = "저장되었습니다!";
        } else if (result == -1) {
            message = "이미 저장된 음반입니다!";
        } else if (result == -2) {
            message = "필수 값을 입력하세요!";
            url = "/addMusicDirectly";
        }

        // Alert 메시지 후, 리다이랙트
        model.addAttribute("message", message);
        model.addAttribute("url", url);
        return "redirect";
    }

    /* 음반 간편 추가 */
    @PostMapping("/addMusicSimply")
    public String addMusicSimply(@ModelAttribute MusicCrawlingRequest musicCrawlingRequest,
                                 Model model) throws IOException {
        int result = musicService.insetMusicSimply(musicCrawlingRequest);
        String message = "";
        String url = "/MusicList";

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
        model.addAttribute("message", message);
        model.addAttribute("url", url);
        return "redirect";
    }

    /* 음반 수정 */
    @PostMapping("/updateMusic")
    public String updateMusic(@ModelAttribute MusicRequest musicRequest, Model model) {
        int result = musicService.updateMusic(musicRequest);
        String message = "";
        String url = "/MusicList";

        if (result == 0) {
            message = "수정되었습니다!";
        } else if (result == -2) {
            message = "필수 값을 입력하세요!";
            url = "/updateMusic";
        }

        // Alert 메시지 후, 리다이랙트
        model.addAttribute("message", message);
        model.addAttribute("url", url);
        return "redirect";
    }

    /* 음반 삭제 */
    @PostMapping("/deleteMusic")
    public String deleteMusic(@RequestParam("item_barcode") String barcode, Model model) {
        int result = musicService.deleteMusic(barcode);

        if (result == -3) {
            // Alert 메시지 후, 리다이랙트
            model.addAttribute("message", "없는 음반입니다!");
            model.addAttribute("url", "/MusicList");
        }

        // 성공 시, alert 없이 리다이랙트
        return "redirect:/MusicList";
    }

    /* 이번 주 감상곡 뽑기 */
    @PostMapping("/chooseMusic")
    public String chooseMusic(Model model) {
        List<Music> thisWeekMusics = musicChooseService.chooseThisWeekMusics();

        if (thisWeekMusics == null) {
            model.addAttribute("message", "곡을 15개 이상 입력하세요!");
            model.addAttribute("url", "/thisWeekMusic");
            return "redirect";
        }

        model.addAttribute("choosedMusicAll", thisWeekMusics);
        model.addAttribute("choosedMusicLength", 15);
        return "thisWeekMusic";
    }
}
