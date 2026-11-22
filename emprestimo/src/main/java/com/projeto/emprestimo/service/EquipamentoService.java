package com.projeto.emprestimo.service;

import com.projeto.emprestimo.exception.ConflitoException;
import com.projeto.emprestimo.exception.NaoEncontradoException;
import com.projeto.emprestimo.model.Equipamento;
import com.projeto.emprestimo.model.TipoEquipamento;
import com.projeto.emprestimo.repository.EmprestimoRepository;
import com.projeto.emprestimo.repository.EquipamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository repository;
    private final EmprestimoRepository emprestimoRepository; // novo: o remover usa

    public EquipamentoService(EquipamentoRepository repository,
                              EmprestimoRepository emprestimoRepository) {
        this.repository = repository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Equipamento> listar(TipoEquipamento tipo){
        if (tipo == null) {
            return repository.findAll(); // retorna tudo
        }
        return repository.findByTipo(tipo); // retorna com filtro

    }

    public List<Equipamento> listar(
            TipoEquipamento tipo, Boolean disponivel) {
        if (tipo != null && disponivel != null) {
            return repository
                    .findByTipoAndDisponivel(tipo, disponivel);
        }
        if (tipo != null) {
            return repository.findByTipo(tipo);
        }
        if (disponivel != null) {
            return repository.findByDisponivel(disponivel);
        }
        return repository.findAll();
    }

    public Equipamento buscar(Long id){
        return repository.findById(id).orElseThrow(() -> // encontre pelo id ou lance essa exceção abaixo
                new NaoEncontradoException("Equipamento não encontrado"));
    }

    // cadastra com regra, recebe um objeto e retorna um objeto (pronto pra salvar no banco)
    public Equipamento cadastrar(Equipamento equipamento) {
        if (repository.existsByPatrimonio(equipamento.getPatrimonio())) { // se o equipamento ja existe nao vai atualizar, vai lançar a exceção
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Patrimônio já cadastrado");
        }
        equipamento.setDisponivel(true);  // todo equipamento novo nasce disponível
        return repository.save(equipamento); // faz um insert no banco de dados
    }

    public Equipamento atualizar(Long id, Equipamento dados) {
        Equipamento equipamento = buscar(id); // 404 se nao existir
        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());

        return repository.save(equipamento);
    }

    public void remover(Long id) {
        Equipamento equipamento = buscar(id);
        if (emprestimoRepository
                .existsByEquipamentoId(id)) {
            throw new ConflitoException(
                    "Equipamento tem empréstimos registrados");
        }
        repository.delete(equipamento);
    }


}
