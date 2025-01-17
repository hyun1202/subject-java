package com.example.subjectjava.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.*;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenProviderTest {
    private static TokenProvider tokenProvider;

    String username = "test1";
    List<String> roles = List.of("ROLE_USER");

    @BeforeAll
    public void init() {
        String secret = "4b2f4cadf8eb823451c1050ad09e67f56c9776251ae68ff1980b186b135b5d6d5e887c19e27846383a36edb42a085161a69b31c03f9563f6674bd4326a7cee3b";
        tokenProvider = new TokenProvider(secret);
    }

    @DisplayName("토큰 발급")
    @Test
    void generateTokenTest() {
        // given - when
        TokenDto tokenDto = getToken();

        System.out.println(tokenDto);

        // then
        Assertions.assertNotNull(tokenDto.accessToken());
        Assertions.assertNotNull(tokenDto.refreshToken());
    }

    @DisplayName("토큰 만료")
    @Test
    void verifyExpirationTest() throws InterruptedException {
        // given
        tokenProvider.setAccessTokenExp(1000L);

        TokenDto token = getToken();
        String accessToken = token.accessToken();

        // when - then
        Assertions.assertTrue(tokenProvider.validateToken(accessToken));
        TimeUnit.MILLISECONDS.sleep(1500);
        Assertions.assertFalse(tokenProvider.validateToken(accessToken));
    }

    @DisplayName("Signature 검증")
    @Test
    void verifySignatureTest() {
        // given
        TokenDto token = getToken();

        // when
        Claims claims = tokenProvider.parseClaims(token.accessToken());

        // then
        Assertions.assertEquals(username, claims.getId());
    }

    private TokenDto getToken() {
        TokenDto tokenDto = tokenProvider.createTokenDto(username, roles);
        return tokenDto;
    }



}