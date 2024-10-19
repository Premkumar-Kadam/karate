package com.ama.karate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.AuthDto;

@Service
public class loginDao {

    @Autowired JdbcTemplate jt;

    //To fetch the password by phone number
    public AuthDto userPassword(String phoneNo) {
        try {
            String SQL = "SELECT password, phone_no AS phoneNo FROM public.user WHERE phone_no = ? AND active = TRUE";
            return jt.queryForObject(SQL, AuthDto.class, phoneNo);
        } catch (DataAccessException e) {
            return new AuthDto();
        }

    }
}
