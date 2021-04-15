package com.s1.awsS3Ex.dto;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GalleryDto {

    private Long id;
    private String title;
    private List<GalleryImage> filePath;

    private List<String> imgFullPath;

    // dto -> entity
    public GalleryEntity toEntity(){

        GalleryEntity galleryEntity = GalleryEntity.builder()
                                        .id(id)
                                        .title(title)
                                        .build();

        return galleryEntity;
    }


    @Builder
    public GalleryDto(Long id, String title, List<GalleryImage> filePath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;

    }
}
