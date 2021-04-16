package com.s1.awsS3Ex.domain.repository;

import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {

    List<GalleryImage> findByGalleryEntity(GalleryEntity galleryEntity);
    List<GalleryImage> deleteByGalleryEntity(GalleryEntity galleryEntity);

}
