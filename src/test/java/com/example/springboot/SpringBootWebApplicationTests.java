package com.example.springboot;

import com.example.springboot.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootWebApplicationTests {
	@Autowired
	private MailService mailService;

	@Test
	public void contextLoads() {
		mailService.sendSimpleMail("1608550717@qq.com","simple mail","hello Mail");
	}

}
