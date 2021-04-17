package com.s1.awsS3Ex.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import com.s1.awsS3Ex.dto.GalleryDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class S3Service {

    public static final String CLOUD_FRONT_DOMAIN_NAME="d1482gbgc9jhx6.cloudfront.net";

    private AmazonS3 s3Client;


    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;





    // 의존성 주입 후 초기화를 수행하는 메서드 -> bean이 한번만 초기화
    @Transactional
    @PostConstruct
    public void setS3Client(){

        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);


        // AmazonS3ClientBuilder를 통해 S3 Client를 가져오는데 -> 자격증명이 필요함
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();

    }


    // 업로드 메소드
    @Transactional
    public List<GalleryImage> upload(GalleryDto galleryDto, MultipartFile[] file) throws IOException {

        List<GalleryImage> gi = new ArrayList<>();


        log.info(" upload 함수 -----------------------");
        log.info("galleryDto = " + galleryDto);

        // 저장/ 수정 업로드 -> 조건문
        if (galleryDto.getFilePath() != null) {

            // 버킷에 해당 key를 가진 객체가 존재하는지 확인한다.
            for (GalleryImage galleryImage : galleryDto.getFilePath()) {


                boolean isExistObject = s3Client.doesObjectExist(bucket, galleryImage.getFileName());

                log.info("isExistObject : "+ isExistObject);
                // 이미지 수정 시, 기존의 이미지를 버킷에 제거해줘야 버킷의 용량을 줄일 수 있다 -> deleteObject로 객체를 제거한다.
                if (isExistObject)
                    s3Client.deleteObject(bucket, galleryImage.getFileName());
            }

        }

        // 이미지 업로드
        for (MultipartFile multipartFile : file) {

            // 고유한 key 값을 갖기위해 현재 시간을 postfix로 붙여줌
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = date.format(new Date()) + "_"+ multipartFile.getOriginalFilename();

            // 파일 신규 업로드, 수정 업로드 모두 같은 메소드를 사용한다.
            s3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));        // public read 권한 추가

            gi.add(new GalleryImage(fileName));
        }

        // 업로드 한 후, 해당 URL을 DB에 저장 할 수 있도록 컨트롤러로 URL 반환
        //return s3Client.getUrl(bucket, filename).toString();

        return gi;
    }


    public void deleteFile(GalleryDto galleryDto) {

        log.info("S3Service ----------------------------------");
        log.info("galleryDto : " +galleryDto);

        if (galleryDto.getFilePath() != null) {

            List<GalleryImage> filePath = galleryDto.getFilePath();

            for (GalleryImage galleryImage : filePath) {

                System.out.println("galleryImage.getFileName() = " + galleryImage.getFileName());
                boolean isExistObject = s3Client.doesObjectExist(bucket, galleryImage.getFileName());

                System.out.println("isExistObject = " + isExistObject);

                if (isExistObject == true) {
                    s3Client.deleteObject(bucket, galleryImage.getFileName());
                }
            }
        }
    }

    public GalleryDto stringToEntity(GalleryDto galleryDto, String[] filePaths){

        List<GalleryImage> gi = new ArrayList<>();

        if (filePaths != null) {

            for (String filePath : filePaths) {
                String substring = filePath.substring(filePath.indexOf("=") + 1, filePath.indexOf(")"));

                gi.add(new GalleryImage(substring));
            }
        }

        galleryDto.setFilePath(gi);
        return galleryDto;

    }

}
