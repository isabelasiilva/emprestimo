package com.projeto.emprestimo.service;

import com.projeto.emprestimo.dto.ColaboradorRequest;
import com.projeto.emprestimo.exception.ConflitoException;
import com.projeto.emprestimo.exception.NaoEncontradoException;
import com.projeto.emprestimo.model.Colaborador;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {
    private final ColaboradorRepository repository;

    public ColaboradorService(ColaboradorRepository repository) {
        this.repository = repository;
    }

    public List<Colaborador> listar() {return repository.findAll();}

    public Colaborador buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Colaborador não encontrado"));
    }

    public Colaborador cadastrar(ColaboradorRequest colaborador) {
        if (repository.existsByEmail(colaborador.email())) {
            throw new ConflitoException("E-mail já cadastrado");
        }
        Colaborador novoColaborador = new Colaborador();
        novoColaborador.setNome(colaborador.nome());
        novoColaborador.setEmail(colaborador.email());
        novoColaborador.setNumero(colaborador.numero());
        novoColaborador.setLogradouro(colaborador.logradouro());
        novoColaborador.setBairro(colaborador.bairro());
        novoColaborador.setCidade(colaborador.cidade());
        novoColaborador.setUf(colaborador.uf());
        novoColaborador.setCep(colaborador.cep());

// quarta-feira: aqui entra a busca do endereço no ViaCEP
        return repository.save(novoColaborador);
    }
}
