package br.com.ceppantoja.cursomc.filter;

import br.com.ceppantoja.cursomc.dto.CredencialDTO;
import br.com.ceppantoja.cursomc.security.UserSS;
import br.com.ceppantoja.cursomc.security.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            CredencialDTO credencialDTO = new ObjectMapper().readValue(request.getInputStream(), CredencialDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credencialDTO.getEmail(), credencialDTO.getSenha(), new ArrayList<>());

            return this.authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = this.jwtUtils.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
    }
}