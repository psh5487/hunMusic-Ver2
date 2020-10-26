package com.music.controller.music;

import com.music.model.music.Music;
import com.music.controller.music.MusicCrawlingRequest;
import com.music.controller.music.MusicRequest;
import com.music.service.music.MusicChooseService;
import com.music.service.music.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/music")
public class MusicRestController {

    @Autowired
    MusicService musicService;

    @Autowired
    MusicChooseService musicChooseService;

    /* 모든 음반 리스트 */
    @GetMapping("/musicList") // GET /MusicList?page=1&size=10&sort=registeredAt,desc
    public ResponseEntity musicList(@PageableDefault(sort="registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<Music> musicList = musicService.findMusicsWithSize(pageable);
        return new ResponseEntity(musicList, HttpStatus.OK);
    }

    /* 음반 직접 추가 */
    @PostMapping("/addMusicDirectly")
    public ResponseEntity addMusicDirectly(@ModelAttribute MusicRequest musicRequest) {
        int result = musicService.insertMusic(musicRequest, "insert");
        String message = "Save Success";
        HttpStatus httpStatus = HttpStatus.OK;

        if (result == -1) {
            message = "Already Exist Music";
            httpStatus = HttpStatus.ALREADY_REPORTED;
        } else if (result == -2) {
            message = "Required Value Empty";
            httpStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity(message, httpStatus);
    }

    /* 음반 간편 추가 */
    @PostMapping("/addMusicSimply")
    public ResponseEntity addMusicSimply(@ModelAttribute MusicCrawlingRequest musicCrawlingRequest) throws IOException {
        int result = musicService.insetMusicSimply(musicCrawlingRequest);
        String message = "Save Success";
        HttpStatus httpStatus = HttpStatus.OK;

        if (result == -1) {
            message = "Already Exist Music";
            httpStatus = HttpStatus.ALREADY_REPORTED;
        } else if (result == -2) {
            message = "Not Enough Music Info. Should Insert Directly";
            httpStatus = HttpStatus.NO_CONTENT;
        } else if (result == -3) {
            message = "No on Sale Music. Should Insert Directly";
            httpStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity(message, httpStatus);
    }

    /* 음반 업데이트 */
    @PostMapping("/updateMusic")
    public ResponseEntity updateMusic(@ModelAttribute MusicRequest musicRequest) {
        int result = musicService.updateMusic(musicRequest);
        String message = "Update Success";
        HttpStatus httpStatus = HttpStatus.OK;

        if (result == -2) {
            message = "Required Value Empty";
            httpStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity(message, httpStatus);
    }

    /* 음반 삭제 */
    @PostMapping("/deleteMusic")
    public ResponseEntity deleteMusic(@RequestParam("item_barcode") String barcode) {
        int result = musicService.deleteMusic(barcode);
        String message = "Delete Success";
        HttpStatus httpStatus = HttpStatus.OK;

        if (result == -3) {
            message = "없는 음반입니다!";
            httpStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity(message, httpStatus);
    }

    /* 이번 주 감상곡 뽑기 */
    @PostMapping("/chooseMusic")
    public ResponseEntity chooseMusic() {
        List<Music> thisWeekMusics = musicChooseService.chooseThisWeekMusics();
        HttpStatus httpStatus = HttpStatus.OK;

        if (thisWeekMusics.isEmpty()) {
            httpStatus = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity(thisWeekMusics, httpStatus);
    }

}
