package com.music.controller.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicCrawlingRequest {
    private String barcode;
    private String category;
    private String composerFromSelect;
    private String composerFromInput;
    private String importance;
}
