package com.ama.karate.dao.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.SessionDto;

@Service
public class loginDao {

    @Autowired JdbcTemplate jt;

    //To fetch the password by phone number
    public AuthDto userPassword(String phoneNo) {
        System.out.println("user phone in dao : 0"+phoneNo);
        try {
            String SQL = "SELECT password, phone_no AS phoneNo FROM public.user WHERE phone_no = ? AND active = TRUE";
    
            return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(AuthDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new AuthDto();
        }
    }

    public SessionDto sessionData(String phoneNo){
        try {
            String SQL = "";
            return jt.queryForObject(SQL, SessionDto.class, phoneNo);
        } catch (DataAccessException e) {
            return new SessionDto();
        }
    }
}
