package com.s1.awsS3Ex.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.net.FileNameMap;

@ToString(exclude = {"galleryEntity"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class GalleryImage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GalleryImage_id")
    private Long id;

    private String FileName;


    @JoinColumn(name = "gallery_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GalleryEntity galleryEntity;



    public GalleryImage(String fileName) {
        FileName = fileName;
    }

    public void setGalleryEntity(GalleryEntity galleryEntity) {
        this.galleryEntity = galleryEntity;
    }
}
