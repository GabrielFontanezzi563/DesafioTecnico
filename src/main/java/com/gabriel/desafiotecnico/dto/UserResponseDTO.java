package com.gabriel.desafiotecnico.dto;

import com.gabriel.desafiotecnico.entity.Role;

public record UserResponseDTO(Long id, String username, Role role) {
}