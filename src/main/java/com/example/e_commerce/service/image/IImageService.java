package com.example.e_commerce.service.image;

import com.example.e_commerce.dto.image.ImageDto;
import com.example.e_commerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> image , Long productId);
    void updateImage(MultipartFile image , Long imageId);

}
