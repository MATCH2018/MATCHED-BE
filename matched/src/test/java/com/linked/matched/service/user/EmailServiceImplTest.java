package com.linked.matched.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @Test
    @DisplayName("이메일 보내기")
    void test() throws Exception {
//        String checkString = emailService.sendSimpleMessage("adad568@naver.com");

    }
}