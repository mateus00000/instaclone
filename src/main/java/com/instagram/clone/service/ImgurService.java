package com.instagram.clone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class ImgurService {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String CLIENT_ID = "868231d7bf61821";

    public String uploadImage(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + CLIENT_ID);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        @SuppressWarnings("rawtypes")
        ResponseEntity<HashMap> response = restTemplate.postForEntity(IMGUR_UPLOAD_URL, requestEntity, HashMap.class);
        @SuppressWarnings("unchecked")
        HashMap<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("data")) {
            @SuppressWarnings("unchecked")
            HashMap<String, String> data = (HashMap<String, String>) responseBody.get("data");
            return data.get("link"); // Retorna a URL da imagem hospedada
        }
        return "Failed to upload image";
    }
}
