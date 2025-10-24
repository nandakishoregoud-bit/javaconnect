package com.javaConnect.web.service;

import com.javaConnect.web.entity.User;

public interface EmailService {

	boolean sendEmail(User user);

}
