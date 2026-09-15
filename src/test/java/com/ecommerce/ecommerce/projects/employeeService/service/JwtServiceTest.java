package com.ecommerce.ecommerce.projects.employeeService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Feature("JWT Authentication")
class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        jwtService = new JwtService(
                jwtEncoder,
                3600000L
        );
    }

    @Test
    @Description("Should generate a JWT token for a valid username")
    @Severity(SeverityLevel.CRITICAL)
    void shouldGenerateToken() {

        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "HS256")
                .claim("sub", "satyam")
                .build();

        when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
                .thenReturn(jwt);

        String result =
                jwtService.generateToken("satyam");

        assertEquals("mock-token", result);

        verify(jwtEncoder)
                .encode(any(JwtEncoderParameters.class));
    }

    @Test
@Description("Should create JWT with correct username")
@Severity(SeverityLevel.CRITICAL)
void shouldCreateTokenWithCorrectUsername() {

    Jwt jwt = Jwt.withTokenValue("mock-token")
            .header("alg", "HS256")
            .claim("sub", "satyam")
            .build();

    when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
            .thenReturn(jwt);

    jwtService.generateToken("satyam");

    ArgumentCaptor<JwtEncoderParameters> captor =
            ArgumentCaptor.forClass(JwtEncoderParameters.class);

    verify(jwtEncoder).encode(captor.capture());

    JwtClaimsSet claims =
            captor.getValue().getClaims();

    assertEquals("satyam", claims.getSubject());
    assertNotNull(claims.getIssuedAt());
    assertNotNull(claims.getExpiresAt());
}

    @Test
@Description("Should set token expiration to one hour")
@Severity(SeverityLevel.NORMAL)
void shouldSetCorrectExpiration() {

    Jwt jwt = Jwt.withTokenValue("mock-token")
            .header("alg", "HS256")
            .claim("sub", "satyam")
            .build();

    when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
            .thenReturn(jwt);

    jwtService.generateToken("satyam");

    ArgumentCaptor<JwtEncoderParameters> captor =
            ArgumentCaptor.forClass(JwtEncoderParameters.class);

    verify(jwtEncoder).encode(captor.capture());

    JwtClaimsSet claims =
            captor.getValue().getClaims();

    Instant issuedAt = claims.getIssuedAt();
    Instant expiresAt = claims.getExpiresAt();

    long difference =
            expiresAt.toEpochMilli()
                    - issuedAt.toEpochMilli();

    assertEquals(3600000L, difference);
}
}