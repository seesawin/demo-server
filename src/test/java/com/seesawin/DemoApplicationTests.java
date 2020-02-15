package com.seesawin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seesawin.models.Person;
import com.seesawin.repository.PersonMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class DemoApplicationTests {
    @Autowired
    PersonMapper personMapper;

    @Test
    void contextLoads() {
        PageHelper.startPage(1, 10);
        PageInfo<Person> pageInfo = new PageInfo<>(personMapper.selectAll());
        System.out.println("end...");
    }

}
