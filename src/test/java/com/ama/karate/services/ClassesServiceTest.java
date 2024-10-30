package com.ama.karate.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ama.karate.dto.ClassesDto;
import com.ama.karate.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClassesServiceTest {

    @Autowired
    private UserService service;

    @Test
    void testBringUserClasses_ReturnsExpectedNumberOfClasses() {
        String phoneNo = "2345678901";

        List<ClassesDto> result = service.bringUserClasses(phoneNo);

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertTrue(!result.isEmpty(), "Expected at least one class returned from the database.");
        
    }
}
