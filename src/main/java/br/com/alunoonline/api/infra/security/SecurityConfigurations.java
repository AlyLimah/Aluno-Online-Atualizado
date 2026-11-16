package br.com.alunoonline.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Mostra ao spring onde está as configurações de segurança, regra de acesso, autenticação e autorização
@EnableWebSecurity //Habilita configuração de segurança do spring
public class SecurityConfigurations {
    //Regras de Segurança
    //1- Como se cadastrar e fazer login
    //2- Recursos que usuário podem acessar
    //3- Métodos de autenticação (senha, tokens, e etc)
    @Autowired
    private SecurityFilter securityFilter;


    @Bean                                     //Objeto spring que permite configurar políticas de segurança da aplicação
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login", "/cadastros").permitAll();
                    req.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**"
                    ).permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build(); //STATEFULL - Servidor não guarda nenhuma informação de usuários
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}




