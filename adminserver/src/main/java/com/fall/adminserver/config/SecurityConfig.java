package com.fall.adminserver.config;

import com.fall.adminserver.constant.Authority;
import com.fall.adminserver.filter.JwtAuthenticationTokenFilter;
import com.fall.adminserver.handler.AuthenticationEntryPointHandler;
import com.fall.adminserver.handler.RequestDeniedHandler;
import com.fall.adminserver.mapper.SysAdminManagerMapper;
import com.fall.adminserver.model.SysUser;
import com.fall.adminserver.model.SecurityLoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/12 下午5:28
 * Security配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SysAdminManagerMapper adminManagerMapper;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    private final RequestDeniedHandler deniedHandler;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                          AuthenticationEntryPointHandler authenticationEntryPointHandler,
                          RequestDeniedHandler deniedHandler,
                          SysAdminManagerMapper adminManagerMapper) {
        this.adminManagerMapper = adminManagerMapper;
        this.deniedHandler = deniedHandler;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.authenticationEntryPointHandler = authenticationEntryPointHandler;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    // 未登录请求接口
    private static final String[] URL_ANONYMOUS = {
            "/sys/login",
    };

    // 接口白名单
    private static final String[] URL_PERMIT_ALL = {
            "/",
            "/error",

            // swagger
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",

            // admin
            // "/admin/register",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(usernamePasswordDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭CSRF,设置无状态连接
        http.csrf().disable()
                // 允许通过basic方式验证用户
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 允许跨域
                .cors()
                .and()
                // 配置异常处理器，处理失败JSON响应
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler).accessDeniedHandler(deniedHandler)
                .and()
                // 将JWT过滤器挂载到用户密码验证过滤之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        // 允许匿名访问的接口
                        authorize
                                .requestMatchers(URL_ANONYMOUS).anonymous()
                                .requestMatchers(URL_PERMIT_ALL).permitAll()
                                .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public UserDetailsService usernamePasswordDetailService() {
        return username -> {

            // 查询管理员信息
            SysUser sysUser = Optional.ofNullable(adminManagerMapper.getSysUserByName(username))
                    .orElseThrow(() -> new BadCredentialsException("用户名或密码错误"));

            // 把数据封装成UserDetails返回
            return new SecurityLoginUser(sysUser, Authority.IntToAuthority(sysUser.getAuthority()));
        };
    }

}
