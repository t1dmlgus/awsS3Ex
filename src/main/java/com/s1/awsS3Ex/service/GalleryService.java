package com.s1.awsS3Ex.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.repository.GalleryRepository;
import com.s1.awsS3Ex.dto.GalleryDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class GalleryService {

    // DB에 저장하는 로직
    private GalleryRepository galleryRepository;
    private S3Service s3Service;


    public void savePost(GalleryDto galleryDto) {

        System.out.println("galleryDto = " + galleryDto);

        GalleryEntity galleryEntity = galleryDto.toEntity();

        System.out.println("galleryEntity = " + galleryEntity);



        galleryRepository.save(galleryDto.toEntity());



    }

    public List<GalleryDto> getList() {

        List<GalleryEntity> galleryEntityList = galleryRepository.findAll();
        List<GalleryDto> galleryDtoList = new ArrayList<>();


        for (GalleryEntity galleryEntity : galleryEntityList) {
            galleryDtoList.add(convertEntityToDto(galleryEntity));
        }

        return galleryDtoList;


    }


    private GalleryDto convertEntityToDto(GalleryEntity galleryEntity) {

        return GalleryDto.builder()
                .id(galleryEntity.getId())
                .title(galleryEntity.getTitle())
                .filePath(galleryEntity.getFilePath())
                .imgFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + galleryEntity.getFilePath())
                .build();



    }


}
