package com.ama.karate.interfaceService;

import java.util.List;

import com.ama.karate.dao.StundetDto;
import com.ama.karate.dto.BeltDto;
import com.ama.karate.dto.ClassesDto;

public interface  MasterInterfaceService {

    List<ClassesDto> bringClassList();

    List<BeltDto> bringBeltList();

    List<StundetDto> bringAllStudents();


}
