package com.demente.ideas.learnwords.auth.filter;

import com.demente.ideas.learnwords.auth.service.JWTService;
import com.demente.ideas.learnwords.auth.service.JWTServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 1987diegog
 * <p>
 * Filtro configurado en security config por eje [addFilter(new JWTAuthenticationFilter(authenticationManager()))]
 * Previo se debe configurar el userDetailsService que utilizara el authentication manager,
 * por ejemplo [AuthenticationManagerBuilder.userDetailsService(jpaUserDetailsService)]
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;

        // Se indica que se accedera al login a traves de un POST a /api/login
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.jwtService = jwtService;
    }

    @Override
    /**
     * Realiza la autentificacion mediante un user y password, para lograrlo utiliza el AuthenticationManager
     */
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if (username != null && password != null) {
            logger.info("[AUTHENTICATION] - Username desde request parameter (form-data): " + username);
            logger.info("[AUTHENTICATION] - Password desde request parameter (form-data): " + password);
        } else {
            com.demente.ideas.learnwords.model.domain.entity.User client = null;
            try {
                client = new ObjectMapper().readValue(request.getInputStream(),
                        com.demente.ideas.learnwords.model.domain.entity.User.class);
                username = client.getUsername();
                password = client.getPassword();

                logger.info("[AUTHENTICATION] - Username desde request InputStream (raw-JSON): " + username);
                logger.info("[AUTHENTICATION] - Password desde request InputStream (raw-JSON): " + password);

            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        username.trim();
        password.trim();

        if (username != null && password != null) {
            logger.info("[AUTHENTICATION] - Username desde request parameter (form-data): " + username);
            logger.info("[AUTHENTICATION] - Password desde request parameter (form-data): " + password);
        }

        logger.info("[AUTHENTICATION] - Se procede a realizar la autentificacion...");
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // para realizar la autentificacion el AuthenticationManager utiliza por debajo la clase UserDetailsService
        // la misma puede ser sobreescrita indicando el mecanismo de logeo, por ejemplo en memoria, bd, etc.
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        logger.info("[AUTHENTICATION] - Usuario autentificado correctamente...");
        String token = jwtService.create(authResult);

        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!",
                ((User) authResult.getPrincipal()).getUsername()));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("mensaje", "Error de autenticación: username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
