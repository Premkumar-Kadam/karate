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

    // To fetch the password by phone number
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
            String SQL = "SELECT DISTINCT " + 
                                "     pu.id AS userLid, " + 
                                "     pu.phone_no AS phoneNo, " + 
                                "     rr.name AS userRole, " + 
                                "     ut.title, " + 
                                "     pu.profile_url AS profileUrl," + 
                                "     bm.colour AS userBelt," + 
                                "     pu.email," + 
                                "     pu.full_name AS fullName," + 
                                "     pu.address," + 
                                "     instructor_stats.total_classes, " + 
                                "     instructor_stats.total_students " + 
                                " FROM " + 
                                "     public.user pu " + 
                                " INNER JOIN " + 
                                "     roles rr ON rr.id = pu.role_id " + 
                                " INNER JOIN " + 
                                "     user_class uc ON uc.user_lid = pu.id " + 
                                " INNER JOIN " + 
                                "     class_master cm ON cm.id = uc.class_lid " + 
                                " INNER JOIN " + 
                                "     belt_master bm ON bm.id = uc.belt_lid " + 
                                " LEFT JOIN " + 
                                "     user_title ut ON ut.user_lid = pu.id " + 
                                " LEFT JOIN (" + 
                                "     SELECT " + 
                                "         instructor.id AS instructor_id, " + 
                                "         COUNT(DISTINCT uc.class_lid) AS total_classes, " + 
                                "         COUNT(DISTINCT student.id) AS total_students " + 
                                "     FROM " + 
                                "         user_class uc " + 
                                "     JOIN " + 
                                "         public.user instructor ON uc.user_lid = instructor.id " + 
                                "     JOIN " + 
                                "         user_class student_classes ON student_classes.class_lid = uc.class_lid " + 
                                "     JOIN " + 
                                "         public.user student ON student_classes.user_lid = student.id " + 
                                "     WHERE " + 
                                "         instructor.role_id = (SELECT id FROM roles WHERE name = 'Instructor') " + 
                                "         AND student.role_id = (SELECT id FROM roles WHERE name = 'Student') " + 
                                "     GROUP BY " + 
                                "         instructor.id" + 
                                " ) AS instructor_stats ON pu.id = instructor_stats.instructor_id" + 
                                " WHERE " + 
                                "     pu.phone_no = ?;";
            return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(SessionDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new SessionDto();
        }
    }

    public AuthDto bringUserEmail(String phoneNo) {
        try {
            String SQL = "SELECT email FROM public.user WHERE phone_no = ? AND active = TRUE";
    
            return jt.queryForObject(SQL, new BeanPropertyRowMapper<>(AuthDto.class), phoneNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new AuthDto();
        }
    }

    public boolean insertOtp(String phoneNo, String otp, String otpFor) {
        String sql = "INSERT INTO otp (phone_no, otp, otp_for, created_by) " +
                        "VALUES (?, ?, ?, ?)";

        try {
            jt.update(sql, phoneNo, otp, otpFor, phoneNo);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isOtpValid(String phoneNo, String otp) {
        String sql = "SELECT COUNT(*) FROM otp WHERE phone_no = ? AND otp = ? AND active = TRUE AND valid_till > NOW()";

        Integer count = jt.queryForObject(sql, Integer.class, phoneNo, otp);
        return count != null && count > 0; // Returns true if OTP is valid
    }

}
