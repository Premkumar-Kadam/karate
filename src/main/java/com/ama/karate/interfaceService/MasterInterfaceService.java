package com.ama.karate.interfaceService;

import java.util.List;

import com.ama.karate.dto.ClassesDto;

public interface  MasterInterfaceService {

    List<ClassesDto> bringClassList(String phoneNo);
}
