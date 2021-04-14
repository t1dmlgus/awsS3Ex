package com.s1.awsS3Ex.dto;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GalleryDto {

    private Long id;
    private String title;
    private String filePath;

    private String imgFullPath;

    // dto -> entity
    public GalleryEntity toEntity(){

        GalleryEntity build = GalleryEntity.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();

        return build;
    }


    @Builder
    public GalleryDto(Long id, String title, String filePath, String imgFullPath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }
}
