package com.security.expences.service.impl;

import com.security.expences.dto.MediaDTO;
import com.security.expences.model.Media;
import com.security.expences.model.User;
import com.security.expences.repository.MediaRepository;
import com.security.expences.service.MediaService;
import com.security.expences.service.UserService;
import com.security.expences.util.MusicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    UserService userService;

    @Autowired
    MediaRepository mediaRepository;

    @Override
    public List<Media> list(String username) {
        User user = userService.findOneByUserName(username);

        return mediaRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Media> createMedia(MediaDTO mediaDTO, String username) {
        User user = userService.findOneByUserName(username);
        Media media = new Media();
        media.setLink(mediaDTO.getLink());
        media.setType(mediaDTO.getType());
        media.setUser(user);
        mediaRepository.save(media);
        return mediaRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Media> deleteMedia(int id, String username) {
        User user = userService.findOneByUserName(username);
        Media media = mediaRepository.getReferenceById(id);
        if(media.getUser().getId() != user.getId()){
            throw new MusicException(HttpStatus.UNAUTHORIZED);
        }
        mediaRepository.delete(media);
        return mediaRepository.findAllByUserId(user.getId());
    }

    @Override
    public void deleteMediaByUserId(int userId) {
        List<Media> mediaList = mediaRepository.findAllByUserId(userId);
        for(Media m : mediaList){
            mediaRepository.delete(m);
        }
    }
}
