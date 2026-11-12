package com.projeto.emprestimo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Setter
@Entity
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O patrimônio é obrigatório")
    @Column(nullable = false, unique = true)
    private String patrimonio;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEquipamento tipo;

    @Column(nullable = false)
    private boolean disponivel = true;

    public Equipamento() { } // construtor vazio é obrigatorio pra rodar

    // getters e setters
}
