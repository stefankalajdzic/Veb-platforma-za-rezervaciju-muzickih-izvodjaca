package com.security.expences.service;

import com.security.expences.dto.LoginRequestDTO;
import com.security.expences.dto.RegistrationRequestDTO;
import com.security.expences.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void registerUserByNameAndUsername(RegistrationRequestDTO request);

    User findOneByUserName(LoginRequestDTO loginRequest);

    User findOneByUserName(String username);

    List<User> listBands(String filter);
    List<User> listBandsForVerification();

    void verifyBand(int bandId);

    void updateUser(User user);

    void disapprove(int id);
}
