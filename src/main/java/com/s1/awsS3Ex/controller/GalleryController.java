package com.s1.awsS3Ex.controller;


import com.s1.awsS3Ex.dto.GalleryDto;
import com.s1.awsS3Ex.service.GalleryService;
import com.s1.awsS3Ex.service.S3Service;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Log4j2
@Controller
@AllArgsConstructor
public class GalleryController {


    private S3Service s3Service;
    private GalleryService galleryService;



    @GetMapping("/gallery")
    public String dispWrite(Model model){


        List<GalleryDto> galleryDtoList = galleryService.getList();

        for (GalleryDto galleryDto : galleryDtoList) {
            log.info(galleryDto);
        }

        model.addAttribute("galleryList", galleryDtoList);

        return "/gallery";
    }


}