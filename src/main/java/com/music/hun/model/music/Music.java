package com.music.hun.model.music;

import com.music.hun.model.userActivity.Comment;
import com.music.hun.model.userActivity.Likes;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private String title;
    private String artist;
    private String composer;
    private String category;
    private String track;
    private String label;
    private String numOfDisc;
    private int importance;
    private LocalDateTime registeredAt;
}
