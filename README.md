# Sistema de Empréstimo de Equipamentos

API REST para controle de empréstimo de equipamentos (notebooks, monitores, headsets) para colaboradores. 

A documentação sobre a proposta exercício e os requisitos está localizada na pasta docs.

## Como rodar localmente

### Backend (API)

Requisitos:
- Java 21
- Maven (ou use o wrapper `./mvnw`)

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`, no Swagger em `http://localhost:8080/swagger-ui/index.html`.

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

## Deploy no Render

A aplicação é dividida em **dois serviços** no Render: um para o backend (API) e outro para o frontend (site estático).

> **Atenção:** no plano gratuito do Render, o disco do container é reiniciado toda vez que o serviço dorme ou é reimplantado. Como o banco é H2 em arquivo (`./dados/emprestimos`), **os dados podem ser perdidos**. Para persistir em produção, use um banco gerenciado pelo Render (PostgreSQL) ou adicione um Render Disk no plano pago.

### 1. Backend (Web Service)

1. No [Render](https://dashboard.render.com/), clique em **New +** → **Web Service**.
2. Conecte o repositório do GitHub/GitLab com este projeto.
3. Preencha:
   - **Name**: `emprestimo-api` (ou o nome que preferir)
   - **Runtime**: `Docker`
   - **Root Directory**: deixe em branco (raiz do projeto)
   - **Branch**: `main` (ou a branch que você usa)
   - **Plan**: Free (ou outro)
4. O `Dockerfile` já está na raiz. O Render usará automaticamente a porta `8080`.
5. Em **Environment Variables**, adicione:
   - `PORT` = `8080`
   - `H2_CONSOLE` = `true` (opcional)
   - `CORS_ALLOWED_ORIGINS` = URL do frontend no Render, por exemplo:
     ```
     https://emprestimo-web.onrender.com
     ```
     Se quiser permitir mais de uma origem, separe por vírgula:
     ```
     https://emprestimo-web.onrender.com,http://localhost:5173
     ```
6. Clique em **Create Web Service**.
7. Após o deploy, anote a URL gerada (ex: `https://emprestimo-api.onrender.com`).

### 2. Frontend (Static Site)

1. No Render, clique em **New +** → **Static Site**.
2. Conecte o mesmo repositório.
3. Preencha:
   - **Name**: `emprestimo-web`
   - **Root Directory**: `view`
   - **Build Command**: `npm install && npm run build`
   - **Publish Directory**: `view/dist`
4. Em **Environment Variables**, adicione:
   - `VITE_API_URL` = URL completa do backend + `/api`, por exemplo:
     ```
     https://emprestimo-api.onrender.com/api
     ```
5. Clique em **Create Static Site**.

### 3. Testando

- Backend: `https://emprestimo-api.onrender.com/equipamentos`
- Swagger: `https://emprestimo-api.onrender.com/swagger-ui/index.html`
- Frontend: `https://emprestimo-web.onrender.com`

### Dica: desenvolvimento local

Localmente, o Vite já faz proxy de `/api` para `http://localhost:8080`. Por isso, quando `VITE_API_URL` não está definida, o frontend usa `/api` automaticamente.

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
