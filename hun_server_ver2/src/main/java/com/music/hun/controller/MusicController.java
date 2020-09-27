package com.music.hun.controller;

import com.music.hun.model.music.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MusicController {
    // GET /users?page=1&size=10&sort=createdAt,desc&sort=userId,asc
//    @RequestMapping("")
//    Page<Music> getMusics(Pageable pageable){
//        return musicService.getList(pageable);
//    }

//    @GetMapping("/hello")
//    public Page<Music> getMusics() {
//        PageRequest request = PageRequest.of(1, 10);
//        return repository.findByName("hello1", request);
//    }

    // Todo: form tag로 부터 넘어온 composer 처리
//    String composer = request.getParameter("composerFromSelect")==null?"":request.getParameter("composerFromSelect");
//	    if(composer.equals("기타") || composer.equals(""))
//    {
//        composer = request.getParameter("composerFromInput")==null?"":request.getParameter("composerFromInput");
//
//        if(composer.equals(""))
//        {
//            composer = "기타";
//        }
//    }
}
