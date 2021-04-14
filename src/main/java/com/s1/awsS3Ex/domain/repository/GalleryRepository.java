package com.s1.awsS3Ex.domain.repository;

import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {

    @Override
    List<GalleryEntity> findAll();
}

