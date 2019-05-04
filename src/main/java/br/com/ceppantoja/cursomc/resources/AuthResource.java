package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.dto.EmailDTO;
import br.com.ceppantoja.cursomc.security.UserSS;
import br.com.ceppantoja.cursomc.security.utils.JWTUtils;
import br.com.ceppantoja.cursomc.service.AuthService;
import br.com.ceppantoja.cursomc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthService service;

    @PostMapping(path = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtils.generateToken(user.getUsername());
        response.addHeader("Authorization", String.format("Bearer %s", token));
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO obj) {
        this.service.sendNewPassword(obj.getEmail());
        return ResponseEntity.noContent().build();
    }
}
