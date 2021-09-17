package com.web.service;

import com.dao.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private BaseDao baseDao;

    public List<?> userAll(){
        return baseDao.selectAllSql("select * from user");
    }
}
