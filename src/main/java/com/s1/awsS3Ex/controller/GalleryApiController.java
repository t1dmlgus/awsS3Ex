package com.s1.awsS3Ex.controller;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.dto.GalleryDto;
import com.s1.awsS3Ex.dto.ResponseDto;
import com.s1.awsS3Ex.service.GalleryService;
import com.s1.awsS3Ex.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@RestController
public class GalleryApiController {

    private final S3Service s3Service;
    private final GalleryService galleryService;


    // 파일 업로드
    @PostMapping("/uploadGallery")
    public ResponseDto<Integer> uploadGallery(GalleryDto galleryDto, String[] filePaths, MultipartFile[] file) throws IOException {

        log.info("파일업로드----------------------");
        log.info("galleryDto = " + galleryDto);

        if (filePaths != null) {
            for (String filePath : filePaths) {
                log.info("filePath = " + filePath);
            }
        }

        for (MultipartFile multipartFile : file) {
            log.info("multipartFile :"+multipartFile.getOriginalFilename());
        }



        // form 이미지 리스트
        List<GalleryImage> images = new ArrayList<>();

        // 업로드
        for (MultipartFile multipartFile : file) {
            log.info("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());

            // s3 업로드 로직
            String imgPath = s3Service.upload(filePaths, multipartFile);
            images.add(new GalleryImage(imgPath));

        }
        log.info("galleryDto111 : "+galleryDto);

        galleryDto.setFilePath(null);
        galleryDto.setFilePath(images);

        galleryService.savePost(galleryDto);


        log.info("galleryDto222 : "+galleryDto);


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
//        return null;
    }



    // 수정
//    @PutMapping("/uploadGallery")
//    public ResponseDto<Integer> galleryUpdate(GalleryDto galleryDto,String[] filePaths,  MultipartFile[] file){
//
//        log.info("galleryDto : "+galleryDto);
//
//        GalleryEntity entity = galleryDto.toEntity();
//
//
//
//        for (String filePath : filePaths) {
//            filePath=filePath.replaceAll("\\[", "").replaceAll("\\]","");
//
//            System.out.println("filePath = " + filePath);
//        }
//
//
//        log.info("file : "+file);
//
//
//
//        return null;
//    }





}
