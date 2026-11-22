package com.projeto.atividade1.service;

import com.projeto.atividade1.model.Recado;
import com.projeto.atividade1.repository.RecadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecadoService {
    private final RecadoRepository recadoRepository;

    public RecadoService(RecadoRepository repository) {
        this.recadoRepository = repository;
    }

    public List<Recado> listar() {
        return recadoRepository.findAll();
    }

    public Recado cadastrar(Recado recado) {
        return recadoRepository.save(recado);
    }
}
