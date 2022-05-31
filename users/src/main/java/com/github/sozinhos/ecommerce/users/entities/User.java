package com.github.sozinhos.ecommerce.users.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String creditCard;

    @NotBlank
    private String cep;
}
