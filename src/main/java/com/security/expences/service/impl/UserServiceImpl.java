package com.security.expences.service.impl;

import com.security.expences.dto.LoginRequestDTO;
import com.security.expences.dto.RegistrationRequestDTO;
import com.security.expences.model.Media;
import com.security.expences.model.User;
import com.security.expences.repository.MediaRepository;
import com.security.expences.repository.UserRepository;
import com.security.expences.service.MediaService;
import com.security.expences.service.UserService;
import com.security.expences.util.MusicException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void registerUserByNameAndUsername(RegistrationRequestDTO request) {
        User user = new User();
        if (userRepository.findOneByUsername(request.getUsername()) != null) {
            throw new MusicException(HttpStatus.BAD_REQUEST, "Korisnik vec postoji");
        }
        if (request.getBandName() != null && !request.getBandName().isEmpty()) {
            if (request.getBandDescription() == null || request.getBandWorkingDays() == null || request.getBandDescription().isEmpty() || request.getBandWorkingDays().isEmpty()) {
                throw new MusicException(HttpStatus.BAD_REQUEST, "Sva polja za bend su obavezna");
            }
        }
        if (request.getBandDescription() != null && !request.getBandDescription().isEmpty()) {
            if (request.getBandName() == null || request.getBandWorkingDays() == null || request.getBandName().isEmpty() || request.getBandWorkingDays().isEmpty()) {
                throw new MusicException(HttpStatus.BAD_REQUEST, "Sva polja za bend su obavezna");
            }
        }
        if (request.getBandWorkingDays() != null && !request.getBandWorkingDays().isEmpty()) {
            if (request.getBandDescription() == null || request.getBandName() == null || request.getBandDescription().isEmpty() || request.getBandName().isEmpty()) {
                throw new MusicException(HttpStatus.BAD_REQUEST, "Sva polja za bend su obavezna");
            }

        }
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBandName(request.getBandName());
        user.setBandDescription(request.getBandDescription());
        user.setBandWorkingDays(request.getBandWorkingDays());
        if (request.getBandName() == null || request.getBandName().isEmpty()) {
            user.setVerified(1);
            user.setPlatformRole("USER");
        }
        else{
                user.setVerified(0);
            };
        userRepository.save(user);
    }

    @Override
    public User findOneByUserName(LoginRequestDTO loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return null;
        }
        User user = userRepository.findOneByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.encode(user.getPassword()).equals(loginRequest.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public User findOneByUserName(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public List<User> listBands(String filter) {
        List<User> usersWithBands = new ArrayList<>();
        List<User> allUsers = new ArrayList<>();
        if (filter == null || filter.isEmpty()) {
            allUsers = userRepository.findAll();
        } else {
            filter = "%" + filter + "%";
            allUsers = userRepository.findAllByBandNameLikeOrBandDescriptionLike(filter, filter);
        }

        for (User user : allUsers) {
            if (user.getVerified() == 1 && Objects.nonNull(user.getBandDescription()) && Objects.nonNull(user.getBandName()) && Objects.nonNull(user.getBandWorkingDays()) && !user.getBandName().trim().isEmpty() && !user.getBandDescription().trim().isEmpty() && !user.getBandWorkingDays().trim().isEmpty()) {
                usersWithBands.add(user);
            }
        }
        return usersWithBands;
    }

    @Override
    public List<User> listBandsForVerification() {
        List<User> usersWithBands = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (user.getVerified() == 0 && Objects.nonNull(user.getBandDescription()) && Objects.nonNull(user.getBandName()) && Objects.nonNull(user.getBandWorkingDays()) && !user.getBandName().trim().isEmpty() && !user.getBandDescription().trim().isEmpty() && !user.getBandWorkingDays().trim().isEmpty()) {
                usersWithBands.add(user);
            }
        }
        return usersWithBands;
    }

    @Override
    public void verifyBand(int bandId){
        User user = userRepository.getOne(bandId);
        user.setVerified(1);
        user.setPlatformRole("USER");
        userRepository.save(user);
    }

    @Override
    public void updateUser(User request){
        User user = userRepository.getOne(request.getId());
        user.setName(request.getName());
        user.setBandName(request.getBandName() == null ? "" : request.getBandName());
        user.setBandDescription(request.getBandDescription()== null ? "" : request.getBandDescription());
        user.setBandWorkingDays(request.getBandWorkingDays()== null ? "" : request.getBandWorkingDays());
        userRepository.save(user);
    }

    @Override
    public void disapprove(int id) {
        User user = userRepository.getOne(id);
        List<Media> mediaList = mediaRepository.findAllByUserId(id);
        for(Media m : mediaList){
            mediaRepository.delete(m);
        }
        user.setBandName("");
        user.setBandDescription("");
        user.setBandWorkingDays("");
        userRepository.save(user);
    }
}
