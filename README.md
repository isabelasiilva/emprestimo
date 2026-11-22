# Sistema de Empréstimo de Equipamentos

API REST para controle de empréstimo de equipamentos (notebooks, monitores, headsets) para colaboradores.

## Como rodar localmente

Requisitos:
- Java 21
- Maven (ou use o wrapper `./mvnw`)

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

Para rodar os testes:

```bash
./mvnw test
```

## Rotas

### Equipamentos

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/equipamentos` | Lista todos os equipamentos (filtros opcionais: `?tipo=NOTEBOOK&disponivel=true`) |
| GET | `/equipamentos/{id}` | Busca um equipamento |
| POST | `/equipamentos` | Cadastra um equipamento |
| PUT | `/equipamentos/{id}` | Atualiza nome e tipo do equipamento |
| DELETE | `/equipamentos/{id}` | Remove um equipamento |

### Colaboradores

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/colaboradores` | Lista todos os colaboradores |
| GET | `/colaboradores/{id}` | Busca um colaborador |
| POST | `/colaboradores` | Cadastra um colaborador |
| GET | `/colaboradores/{id}/emprestimos` | Histórico de empréstimos do colaborador |

### Empréstimos

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/emprestimos` | Lista todos os empréstimos (`?atrasados=true` para só atrasados) |
| GET | `/emprestimos/{id}` | Busca um empréstimo |
| POST | `/emprestimos` | Cria um empréstimo (body: `{ "equipamentoId": 1, "colaboradorId": 2 }`) |
| PUT | `/emprestimos/{id}/devolucao` | Registra a devolução |

## Deploy

A API está publicada no Render:

```
https://SEU-PROJETO-AQUI.onrender.com
```

> Substitua pelo endereço gerado no Render.

## Bônus implementados

- Swagger / OpenAPI: `http://localhost:8080/swagger-ui/index.html`
- Validação com Bean Validation (`@NotBlank`, `@Email`, `@NotNull`)
- Tratamento de erros com `@RestControllerAdvice` e status HTTP adequados (404, 409, 400)
- Relacionamento `@ManyToOne` entre `Emprestimo`, `Equipamento` e `Colaborador`
