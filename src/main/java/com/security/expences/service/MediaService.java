package com.security.expences.service;

import com.security.expences.dto.MediaDTO;
import com.security.expences.model.Media;

import java.util.List;

public interface MediaService {
    List<Media> list(String username);

    List<Media> createMedia(MediaDTO media, String username);

    List<Media> deleteMedia(int media, String username);

    void deleteMediaByUserId(int userId);
}
