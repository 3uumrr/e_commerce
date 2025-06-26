package com.example.e_commerce.dto.image;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String imageName;
    private String downloadUrl;
}
