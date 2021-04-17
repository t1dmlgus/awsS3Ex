package com.s1.awsS3Ex.controller;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.dto.GalleryDto;
import com.s1.awsS3Ex.dto.ResponseDto;
import com.s1.awsS3Ex.service.GalleryService;
import com.s1.awsS3Ex.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.internal.util.StringHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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


    /**
     *
     * 파일 업로드 (AWS S3)
     *
     * @param galleryDto
     * @param filePaths
     * @param file
     * @return
     * @throws IOException
     */


    // 파일 업로드
    @PostMapping("/uploadGallery")
    public ResponseDto<Integer> uploadGallery(GalleryDto galleryDto, String[] filePaths, MultipartFile[] file) throws IOException {

        log.info("파일업로드----------------------");
        log.info("galleryDto = " + galleryDto);


        //filePaths 변환 String[] -> List<GalleryImage>
        if (filePaths != null) {
            for (String filePath : filePaths) {
                log.info("filePath = " + filePath);
            }
            // 변환 비즈니스 로직
            galleryDto.convertStringToDto(filePaths);

        }
        log.info("galleryDto 컨버트 하고 = " + galleryDto);

        for (MultipartFile multipartFile : file) {
            log.info("multipartFile :"+multipartFile.getOriginalFilename());
        }
        
        
        // s3 비즈니스 로직
        List<GalleryImage> upload = s3Service.upload(galleryDto, file);
        galleryDto.setFilePath(upload);

        log.info("galleryDto 업로드 끝내고 = " + galleryDto);



        // Repository에 저장(entity, image)
        galleryService.savePost(galleryDto);


        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }




    /**
     *
     * 파일 삭제 (S3 -> Repository(Entity -> Image))
     *
     * @param galleryDto
     * @param filePaths
     * @return ResponseDto<Integer>
     */

    @DeleteMapping("/uploadGallery")
    public ResponseDto<Integer> deleteGallery(GalleryDto galleryDto, String[] filePaths){

        // 넘어온 매개변수 확인
        log.info("galleryDto : "+galleryDto);
        for (String filePath : filePaths) {
            log.info("filePath : " +filePath);
        }

        // 넘어온 파일 이미지 리스트 담을 임시 변수
        List<GalleryImage> gi = new ArrayList<>();

        // String[] filePath -> List<GalleryImage> 타입변경
        for (String filePath: filePaths) {
            String substring = filePath.substring(filePath.indexOf("=") + 1, filePath.indexOf(")"));

            gi.add(new GalleryImage(substring));
        }

        galleryDto.setFilePath(gi);
        log.info("galleryDto : "+galleryDto);

        // S3 이미지 삭제
        s3Service.deleteFile(galleryDto);
        galleryService.deletePost(galleryDto);

        return new ResponseDto<>(HttpStatus.OK.value(), 1);
    }

}
