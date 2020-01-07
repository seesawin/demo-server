package com.seesawin.controllers;


import com.seesawin.models.ERole;
import com.seesawin.models.Roles;
import com.seesawin.models.Users;
import com.seesawin.payload.request.LoginRequest;
import com.seesawin.payload.request.SignupRequest;
import com.seesawin.payload.response.JwtResponse;
import com.seesawin.payload.response.MessageResponse;
import com.seesawin.repository.RolesMapper;
import com.seesawin.repository.UsersMapper;
import com.seesawin.security.jwt.JwtUtils;
import com.seesawin.security.services.UserDetailsImpl;
import com.seesawin.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(">>>> signin: " + loginRequest.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws Exception {
        System.out.println(">>>> signup: " + signUpRequest.toString());

        Users users = usersMapper.selectAll().stream()
                .filter(user -> user.getUsername().equals(signUpRequest.getUsername()))
                .findAny()
                .orElse(null);
        if (users != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        users = usersMapper.selectAll().stream()
                .filter(user -> user.getEmail().equals(signUpRequest.getEmail()))
                .findAny()
                .orElse(null);
        if (users != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Users user = new Users();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        List<Roles> roleList = rolesMapper.selectAll();
        if (strRoles == null) {
            Roles userRole = roleList.stream()
                    .filter(role -> role.getName().equals(ERole.ROLE_USER.name()))
                    .findAny().orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleList.stream()
                                .filter(r -> r.getName().equals(ERole.ROLE_ADMIN.name()))
                                .findAny().orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Roles modRole = roleList.stream()
                                .filter(r -> r.getName().equals(ERole.ROLE_MODERATOR.name()))
                                .findAny().orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Roles userRole = roleList.stream()
                                .filter(r -> r.getName().equals(ERole.ROLE_USER.name()))
                                .findAny().orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        authService.saveUserAndRoles(user, roles);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
