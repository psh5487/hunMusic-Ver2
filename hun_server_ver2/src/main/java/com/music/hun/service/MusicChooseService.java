package com.music.hun.service;

import com.music.hun.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicChooseService {
    @Autowired
    private MusicRepository musicRepository;


}
