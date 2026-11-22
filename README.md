# Sistema de Empréstimo de Equipamentos

Projeto do segundo módulo do bootcamp ElasTech com java + react 

Esse projeto contém uma API REST para controle de empréstimo de equipamentos (notebooks, monitores, headsets) para colaboradores. 

A documentação sobre a proposta exercício e os requisitos está localizada na pasta docs.

Os projetos paralelos em src/main/java/com/projeto/atividade1 e atividade2 foram criados para aproveitar as configurações de ambiente, não interferindo no funcionamento do projeto emprestimos, que é a principal entrega do segundo módulo do curso.

## Como rodar localmente

### Backend (API)

Requisitos:
- Java 21
- Maven (ou use o wrapper `./mvnw`)

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`, no Swagger em `http://localhost:8080/swagger-ui/index.html`.
Após rodar a API, o banco H2 é acessado pela rota `http://localhost:8080/h2-console`

Para rodar os testes:

```bash
./mvnw test
```


### Frontend (React + Vite)

Requisitos:
- Node.js 18+

Instale as dependências na primeira vez:

```bash
cd view
npm install
```

Inicie o servidor de desenvolvimento:

```bash
npm run dev
```

O front sobe em `http://localhost:5173`.

> O Vite já está configurado com proxy para a API em `http://localhost:8080`. Todas as chamadas feitas para `/api` no frontend são redirecionadas automaticamente ao backend.

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


## Frontend

A interface está em `./view/` e foi criada com **React + Vite**. Ela consome a API REST e possui três telas principais:

- **Equipamentos**: listagem com filtros, cadastro, edição e remoção.
- **Colaboradores**: cadastro e visualização do histórico de empréstimos.
- **Empréstimos**: criação de novos empréstimos, listagem (com filtro de atrasados) e devolução.

A comunicação com o backend usa o arquivo `view/src/api.js`. Em desenvolvimento, o Vite faz proxy de `/api` para `http://localhost:8080`, configurado em `view/vite.config.js`.


## Bônus implementados

- Swagger / OpenAPI: `http://localhost:8080/swagger-ui/index.html`
- Validação com Bean Validation (`@NotBlank`, `@Email`, `@NotNull`)
- Tratamento de erros com `@RestControllerAdvice` e status HTTP adequados (404, 409, 400)
- Relacionamento `@ManyToOne` entre `Emprestimo`, `Equipamento` e `Colaborador`
- Frontend React simples consumindo a API com proxy configurado

## Rotas no Render
- Frontend: `https://emprestimo-frontend.onrender.com/` 
- Backend: `https://emprestimo-ygbw.onrender.com/`

# Video de apresentação apresentação
`https://www.youtube.com/watch?v=NYSPxp3YzsU`
