package br.com.ceppantoja.cursomc.resources;

import br.com.ceppantoja.cursomc.security.UserSS;
import br.com.ceppantoja.cursomc.security.utils.JWTUtils;
import br.com.ceppantoja.cursomc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping(path = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtils.generateToken(user.getUsername());
        response.addHeader("Authorization", String.format("Bearer %s", token));
        return ResponseEntity.noContent().build();
    }
}
