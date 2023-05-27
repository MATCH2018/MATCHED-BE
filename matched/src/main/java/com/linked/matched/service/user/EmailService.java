package com.linked.matched.service.user;

import com.linked.matched.request.user.UserEmail;

public interface EmailService {
    String sendSimpleMessage(UserEmail to)throws Exception;
}
