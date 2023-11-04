package com.msa.config;

import com.msa.auth.JwtAuthFilter;
import com.msa.auth.JwtTokenProvider;
import com.msa.member.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/signup", "/members/auth/**",
                        "/h2-console/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                
                //토큰 승인 필터 -> 1. username과 비밀번호 authenticate.
                // 추가된 필터에서
                // SecurityContextHolder.getContext().setAuthentication(authentication);
                // 를 설정해줬기 떄문에 5. 번에서 글이 보임(그림 참조)
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();



    }

}
