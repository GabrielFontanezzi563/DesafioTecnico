package com.gabriel.desafiotecnico.controller;

import com.gabriel.desafiotecnico.dto.UserResponseDTO;
import com.gabriel.desafiotecnico.entity.User;
import com.gabriel.desafiotecnico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserResponseDTO> listarTodos() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getRole()))
                .toList();
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> criarUsuario(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User usuarioSalvo = userRepository.save(user);

        UserResponseDTO dto = new UserResponseDTO(usuarioSalvo.getId(), usuarioSalvo.getUsername(), usuarioSalvo.getRole());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody User dadosAtualizados) {

        Optional<User> userExistente = userRepository.findById(id);

        if (userExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User userParaAtualizar = userExistente.get();

        userParaAtualizar.setUsername(dadosAtualizados.getUsername());
        userParaAtualizar.setRole(dadosAtualizados.getRole());

        if (dadosAtualizados.getPassword() != null && !dadosAtualizados.getPassword().isBlank()) {
            String novaSenhaCriptografada = passwordEncoder.encode(dadosAtualizados.getPassword());
            userParaAtualizar.setPassword(novaSenhaCriptografada);
        }

        User usuarioSalvo = userRepository.save(userParaAtualizar);

        UserResponseDTO dto = new UserResponseDTO(usuarioSalvo.getId(), usuarioSalvo.getUsername(), usuarioSalvo.getRole());

        return ResponseEntity.ok(dto);
    }
}