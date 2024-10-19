package com.ama.karate.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.ama.karate.dao.loginDao;
import com.ama.karate.dto.AuthDto;
import com.ama.karate.service.AuthService;
import com.ama.karate.utils.PasswordService;

@SpringBootTest
public class AuthTest {
    @Mock
    private loginDao login;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private AuthService authenticationService;

    private AuthDto requestAuthDto;

    private AuthDto dbAuthDto;

    @BeforeEach
    public void setUp() {
        requestAuthDto = new AuthDto();
        requestAuthDto.setPhoneNo("1234567890");
        requestAuthDto.setPassword("user_password");

        dbAuthDto = new AuthDto();
        dbAuthDto.setPhoneNo("1234567890");
        dbAuthDto.setPassword("hashed_password");
    }

    @Test
    public void testIsAuthenticated_SuccessfulAuthentication() {
        when(login.userPassword("1234567890")).thenReturn(dbAuthDto);
        when(passwordService.comparePassword("user_password", "hashed_password")).thenReturn(true);

        boolean isAuthenticated = authenticationService.isAuthenticated(requestAuthDto);
        assertTrue(isAuthenticated);
    }

    @Test
    public void testIsAuthenticated_IncorrectPassword() {
        when(login.userPassword("1234567890")).thenReturn(dbAuthDto);
        when(passwordService.comparePassword("user_password", "hashed_password")).thenReturn(false);

        boolean isAuthenticated = authenticationService.isAuthenticated(requestAuthDto);
        assertFalse(isAuthenticated);
    }

    @Test
    public void testIsAuthenticated_NullPasswordInDb() {
        dbAuthDto.setPassword(null);
        when(login.userPassword("1234567890")).thenReturn(dbAuthDto);

        boolean isAuthenticated = authenticationService.isAuthenticated(requestAuthDto);
        assertFalse(isAuthenticated);
    }

    @Test
    public void testIsAuthenticated_UserNotFound() {
        when(login.userPassword("1234567890")).thenReturn(null);

        boolean isAuthenticated = authenticationService.isAuthenticated(requestAuthDto);
        assertFalse(isAuthenticated);
    }
}
