package com.example.e_commerce.service.image;

import com.example.e_commerce.dto.image.ImageDto;
import com.example.e_commerce.exceptions.ImageNotFoundException;
import com.example.e_commerce.model.Image;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.ImageRepository;
import com.example.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.delete(getImageById(id));
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImagesDto = new ArrayList<>();

        for (MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId()); // To update id
                imageRepository.save(image);

                ImageDto imageDto = ImageDto.builder()
                        .id(savedImage.getId())
                        .imageName(savedImage.getFileName())
                        .downloadUrl(savedImage.getDownloadUrl())
                        .build();

                savedImagesDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException("An error occurred while adding the image data (filename, type, or content).", e);
            }


        }

        return savedImagesDto;


    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException("An error occurred while updating the image data (filename, type, or content).", e);
        }
    }
}
