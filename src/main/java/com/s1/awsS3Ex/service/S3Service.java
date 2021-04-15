package com.s1.awsS3Ex.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.s1.awsS3Ex.domain.entity.GalleryImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    public String upload(List<GalleryImage> currentFilePath, MultipartFile file) throws IOException {

        // 고유한 key 값을 갖기위해 현재 시간을 postfix로 붙여줌
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = date.format(new Date()) + "_"+ file.getOriginalFilename();


        log.info(currentFilePath);


//        // 저장/ 수정 업로드 -> 조건문
//        if ("".equals(currentFilePath) == false && currentFilePath != null) {
//
//
//
//            // 버킷에 해당 key를 가진 객체가 존재하는지 확인한다.
//            boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);
//
//
//            // 이미지 수정 시, 기존의 이미지를 버킷에 제거해줘야 버킷의 용량을 줄일 수 있다 -> deleteObject로 객체를 제거한다.
//            if(isExistObject == true)
//                s3Client.deleteObject(bucket, currentFilePath);
//
//        }
//



        // 파일 신규 업로드, 수정 업로드 모두 같은 메소드를 사용한다.
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));        // public read 권한 추가


        // 업로드 한 후, 해당 URL을 DB에 저장 할 수 있도록 컨트롤러로 URL 반환
        //return s3Client.getUrl(bucket, filename).toString();

        return fileName;
    }






}
