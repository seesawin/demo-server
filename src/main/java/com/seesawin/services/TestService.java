package com.seesawin.services;

import com.seesawin.models.Roles;
import com.seesawin.models.UserRoles;
import com.seesawin.models.Users;
import com.seesawin.repository.UserRolesMapper;
import com.seesawin.repository.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@CacheConfig(cacheNames = "TestService")
@Service
@Transactional(rollbackFor = Exception.class)
public class TestService {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    UserRolesMapper userRolesMapper;

    public void saveUserAndRoles(Users user, Set<Roles> roles) throws Exception {
        usersMapper.insert(user);
        roles.forEach(r -> {
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(user.getId());
            userRoles.setRoleId(r.getId());
            userRolesMapper.insert(userRoles);
        });
    }

    @Cacheable(key = "'greet'.concat({#key})")
    public String greet(String key) {
        log.info("快取內沒有取到，執行service key={}", key);
        return "world！";
    }
}
