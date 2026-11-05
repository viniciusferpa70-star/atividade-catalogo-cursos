package br.edu.catalogocursos.api.dto;
import jakarta.validation.constraints.*;
public record InstrutorRequest(@NotBlank @Size(min=3,max=120) String nome,@NotBlank @Email @Size(max=160) String email){}
