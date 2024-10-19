package com.ama.karate.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.interfaceService.MasterInterfaceService;

@Service
public class MasterDao implements MasterInterfaceService{
    @Autowired JdbcTemplate jt;

    //Get List of classes
    @Override
    public List<ClassesDto> classList(String phoneNo) {
        try {
            String SQL = "SELECT id AS classLid, name AS className, address AS classAddress, city AS classCity, fees AS classFees, " +
                            " admission_fees AS admissionFees, is_main AS isMain " +
                            " FROM class_master cm WHERE cm.active = TRUE;";

            return jt.queryForList(SQL, ClassesDto.class);
        } catch (DataAccessException e) {
            return new ArrayList<ClassesDto>();
        }

    }
}
