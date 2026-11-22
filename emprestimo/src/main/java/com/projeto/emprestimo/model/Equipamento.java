package com.projeto.emprestimo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O patrimônio é obrigatório!")
    @Column(nullable = false, unique = true)
    private String patrimonio;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O tipo é obrigatório!")
    @Enumerated(EnumType.STRING) // como enum é guardado no banco
    @Column(nullable = false)
    private TipoEquipamento tipo;

    @Column(nullable = false)
    private boolean disponivel = true;

    public Equipamento() { } // obrigatorio pra rodar

    public Long getId() {
        return id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public String getNome() {
        return nome;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
