package com.s1.awsS3Ex.service;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.domain.repository.GalleryImageRepository;
import com.s1.awsS3Ex.domain.repository.GalleryRepository;
import com.s1.awsS3Ex.dto.GalleryDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class GalleryService {

    // DB에 저장하는 로직
    private GalleryRepository galleryRepository;
    private GalleryImageRepository galleryImageRepository;
    private S3Service s3Service;


    
    // gallery 저장
    @Transactional
    public void savePost(GalleryDto galleryDto) {

        // gallery 저장
        System.out.println("galleryDto = " + galleryDto);
        //GalleryEntity entity = galleryDto.toEntity();
        GalleryEntity entity = galleryRepository.save(galleryDto.toEntity());

        System.out.println("entity333 = " + entity);


        galleryImageRepository.deleteByGalleryEntity(entity);

        // gallery image 저장
        List<GalleryImage> filePath = galleryDto.getFilePath();
        for (GalleryImage galleryImage : filePath) {

            galleryImage.setGalleryEntity(entity);

            galleryImageRepository.save(galleryImage);
            System.out.println("galleryImage22 = " + galleryImage);
        }



    }
    // 수정
    @Transactional
    public void updatePost(GalleryDto galleryDto) {


    }


    // gallery List 조회
    @Transactional
    public List<GalleryDto> getList() {

        // 갤러리엔티티 리스트
        List<GalleryEntity> galleryEntityList = galleryRepository.findAll();

        // 갤러리 dto 리스트
        List<GalleryDto> galleryDtoList = new ArrayList<>();



        for (GalleryEntity galleryEntity : galleryEntityList) {

            log.info("galleryEntity = " + galleryEntity);

            // 갤러리 엔티티 - 리스트
            List<GalleryImage> galleryImages = galleryImageRepository.findByGalleryEntity(galleryEntity);

            // imgPath 리스트 -> 지정된 엔티티에서만 이미지 리스트 -> 생성, 초기화
            List<String> imgPaths = new ArrayList<>();

            for (GalleryImage galleryImage : galleryImages) {

                log.info("galleryImage : " + galleryImage);

                String imgPath = "https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + galleryImage.getFileName();
                imgPaths.add(imgPath);
            }
//            for (String s1 : imgPaths) {
//                System.out.println("s1 = " + s1);
//            }

            galleryDtoList.add(convertEntityToDto(galleryEntity, galleryImages, imgPaths));

        }
        return galleryDtoList;

    }

    // EntityList -> DtoList
    private GalleryDto convertEntityToDto(GalleryEntity galleryEntity, List<GalleryImage> galleryImages, List<String> imgPaths) {

        return GalleryDto.builder()
                .id(galleryEntity.getId())
                .title(galleryEntity.getTitle())
                .filePath(galleryImages)
                .imgFullPath(imgPaths)
                .build();

    }



}
