package com.gabriel.desafiotecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioTecnicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioTecnicoApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner initData(
            com.gabriel.desafiotecnico.repository.UserRepository userRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                com.gabriel.desafiotecnico.entity.User admin = com.gabriel.desafiotecnico.entity.User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(com.gabriel.desafiotecnico.entity.Role.ROLE_ADMIN)
                        .build();

                userRepository.save(admin);
                System.out.println("✅ Utilizador Admin criado com sucesso! (Username: admin | Senha: admin123)");
            }
        };
    }
}
