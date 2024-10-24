package com.ama.karate.dao.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.BeltDto;
import com.ama.karate.dto.ClassesDto;
import com.ama.karate.dto.StudentDto;
import com.ama.karate.interfaceService.MasterInterfaceService;

@Service
public class MasterDao implements MasterInterfaceService{
    @Autowired JdbcTemplate jt;

    //Get List of classes
    @Override
    public List<ClassesDto> bringClassList() {
        try {
            String SQL = "SELECT id AS classLid, name AS className, address AS classAddress, city AS classCity, fees AS classFees, " +
                         "admission_fees AS admissionFees, is_main AS isMain " +
                         "FROM class_master cm WHERE cm.active = TRUE;";

            return jt.query(SQL, new BeanPropertyRowMapper<>(ClassesDto.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<BeltDto> bringBeltList() {
        try {
            String SQL = "SELECT colour, fees, level, portion, description FROM belt_master WHERE active = true;";
    
            return jt.query(SQL, new BeanPropertyRowMapper<>(BeltDto.class));
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<StudentDto> bringAllStudents() {
        try {
            String SQL = "";

            return jt.queryForList(SQL, StudentDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<StudentDto>();
        }
    }
}
