package com.s1.awsS3Ex.controller;


import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.dto.GalleryDto;
import com.s1.awsS3Ex.dto.ResponseDto;
import com.s1.awsS3Ex.service.GalleryService;
import com.s1.awsS3Ex.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.HttpOptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseDto<Integer> uploadGallery(GalleryDto galleryDto, MultipartFile[] file) throws IOException {

        System.out.println("galleryDto = " + galleryDto);

        List<GalleryImage> images = new ArrayList<>();

        for (MultipartFile multipartFile : file) {
            System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());

            String imgPath = s3Service.upload(galleryDto.getFilePath(), multipartFile);

            images.add(new GalleryImage(imgPath));


        }
        galleryDto.setFilePath(images);


        log.info("galleryDto.imgPath : "+galleryDto);


        galleryService.savePost(galleryDto);


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }
}
