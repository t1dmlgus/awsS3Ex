package com.s1.awsS3Ex.service;


import com.s1.awsS3Ex.domain.entity.GalleryEntity;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.domain.repository.GalleryImageRepository;
import com.s1.awsS3Ex.domain.repository.GalleryRepository;
import com.s1.awsS3Ex.dto.GalleryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


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
        GalleryEntity galleryEntity = galleryDto.toEntity();
        GalleryEntity entity = galleryRepository.save(galleryDto.toEntity());

        System.out.println("entity333 = " + entity);



        // gallery image 저장
        List<GalleryImage> filePath = galleryDto.getFilePath();
        for (GalleryImage galleryImage : filePath) {

            System.out.println("galleryImage11 = " + galleryImage);

            galleryImage.setGalleryEntity(entity);

            galleryImageRepository.save(galleryImage);
            System.out.println("galleryImage22 = " + galleryImage);
        }



    }

    // gallery List 조회
    @Transactional
    public List<GalleryDto> getList() {

        // 갤러리엔티티 리스트
        List<GalleryEntity> galleryEntityList = galleryRepository.findAll();

        // 갤러리 dto 리스트
        List<GalleryDto> galleryDtoList = new ArrayList<>();


        for (GalleryEntity galleryEntity : galleryEntityList) {

            System.out.println("galleryEntity = " + galleryEntity);


            // 갤러리 엔티티 - 리스트
//            List<GalleryImage> galleryImages = galleryImageRepository.findByGalleryEntity(galleryEntity.getId());
//

//            for (GalleryImage galleryImage : galleryImages) {
//
//                galleryDto.setFilePath(galleryImages);
//            }
//
//            galleryDtoList.add(convertEntityToDto(galleryEntity));
        }

        return galleryDtoList;


    }

    // EntityList -> DtoList
    @Transactional
    private GalleryDto convertEntityToDto(GalleryEntity galleryEntity) {

        return GalleryDto.builder()
                .id(galleryEntity.getId())
                .title(galleryEntity.getTitle())
//                .filePath(galleryEntity.getFilePath())
//                .imgFullPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + galleryEntity.getFilePath())
                .build();


    }


}
