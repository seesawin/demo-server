package com.seesawin.controllers;

import com.hazelcast.core.Hazelcast;
import com.seesawin.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/cache")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Object cache(@RequestParam String key) {
        String value = testService.greet(key);
        log.info("從分散式快取獲取到 key={},value={}", key, value);
        return value;
    }

    @GetMapping("/greet")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Object greet(@RequestParam String key) {
        Object value = Hazelcast.getHazelcastInstanceByName("hazelcast-instance").getMap("TestService").get(key);
        if (Objects.isNull(value)) {
            String cacheValue = key + " greet!";
            Hazelcast.getHazelcastInstanceByName("hazelcast-instance").getMap("TestService").put(key, cacheValue);
            log.info("設定快取至緩存 key={},value={}", key, cacheValue);
        }
        log.info("從分散式快取獲取到 key={},value={}", key, value);
        return value;
    }

    @GetMapping("/session")
    public Object session(HttpSession session) {
        String sessionId = session.getId();
        log.info("當前請求的sessionId={}", sessionId);
        return sessionId;
    }

}
