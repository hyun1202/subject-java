package com.example.subjectjava.security.jwt;

import org.junit.jupiter.api.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenProviderTest {
    private static TokenProvider tokenProvider;

    @BeforeAll
    public void init() {
        String secret = "4b2f4cadf8eb823451c1050ad09e67f56c9776251ae68ff1980b186b135b5d6d5e887c19e27846383a36edb42a085161a69b31c03f9563f6674bd4326a7cee3b";
        tokenProvider = new TokenProvider(secret);
    }

    @DisplayName("토큰 발급")
    @Test
    void generateTokenTest() {
        String username = "test1";
        List<String> roles = List.of("ROLE_USER");

        TokenDto tokenDto = tokenProvider.createTokenDto(username, roles);

        System.out.println(tokenDto);

        Assertions.assertNotNull(tokenDto.accessToken());
        Assertions.assertNotNull(tokenDto.refreshToken());
    }

}