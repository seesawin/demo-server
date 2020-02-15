package com.seesawin.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seesawin.models.Person;
import com.seesawin.repository.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageService {
    @Autowired
    PersonMapper personMapper;

    public PageInfo<Person> getPerson(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(personMapper.selectAll());
    }
}
