package com.music.hun.model.music;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequest {
    private String barcode;
    private String title;
    private String artist;
    private String composerFromSelect;
    private String composerFromInput;
    private String category;
    private String track;
    private String label;
    private String numOfDisc;
    private String importance;
}
