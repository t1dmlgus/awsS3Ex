package com.s1.awsS3Ex.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString(exclude = {"filePath"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "gallery")
public class GalleryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;


    // filePath 필드로 AWS S3에 저장된 파일 경로를 DB에 저장한다.
    @OneToMany
    private List<GalleryImage> filePath;


    @Builder
    public GalleryEntity(Long id, String title) {
        this.id = id;
        this.title = title;
    }


}
