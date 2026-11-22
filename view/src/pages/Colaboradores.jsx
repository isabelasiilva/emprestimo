import { useEffect, useState } from "react";
import { api } from "../api";

export function Colaboradores() {
  const [lista, setLista] = useState([]);
  const [form, setForm] = useState({ nome: "", email: "", cargo: "" });
  const [erro, setErro] = useState("");
  const [historico, setHistorico] = useState(null);

  async function carregar() {
    try {
      const dados = await api.colaboradores.listar();
      setLista(dados);
      setErro("");
    } catch (e) {
      setErro(e.message);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function salvar(e) {
    e.preventDefault();
    try {
      await api.colaboradores.criar(form);
      setForm({ nome: "", email: "", cargo: "" });
      await carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  async function verHistorico(id) {
    try {
      const dados = await api.colaboradores.emprestimos(id);
      setHistorico({ id, dados });
    } catch (e) {
      setErro(e.message);
    }
  }

  return (
    <div className="page">
      <h2>Colaboradores</h2>

      {erro && <div className="erro">{erro}</div>}

      <form onSubmit={salvar} className="form">
        <h3>Novo colaborador</h3>
        <input
          name="nome"
          placeholder="Nome"
          value={form.nome}
          onChange={handleChange}
          required
        />
        <input
          name="email"
          type="email"
          placeholder="E-mail"
          value={form.email}
          onChange={handleChange}
          required
        />
        <input
          name="cargo"
          placeholder="Cargo (opcional)"
          value={form.cargo}
          onChange={handleChange}
        />
        <button type="submit">Cadastrar</button>
      </form>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>E-mail</th>
            <th>Cargo</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {lista.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.nome}</td>
              <td>{item.email}</td>
              <td>{item.cargo || "-"}</td>
              <td>
                <button className="small" onClick={() => verHistorico(item.id)}>
                  Empréstimos
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {historico && (
        <div className="modal-overlay" onClick={() => setHistorico(null)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h3>Histórico de empréstimos</h3>
            {historico.dados.length === 0 ? (
              <p>Nenhum empréstimo encontrado.</p>
            ) : (
              <ul className="list">
                {historico.dados.map((e) => (
                  <li key={e.id}>
                    <strong>Equipamento:</strong> {e.equipamento.nome} —{" "}
                    <strong>Retirada:</strong> {e.dataEmprestimo} —{" "}
                    <strong>Devolução prevista:</strong> {e.dataPrevistaDevolucao} —{" "}
                    {e.dataDevolucao ? (
                      <span className="badge ok">Devolvido em {e.dataDevolucao}</span>
                    ) : e.atrasado ? (
                      <span className="badge danger">Atrasado</span>
                    ) : (
                      <span className="badge warning">Em andamento</span>
                    )}
                  </li>
                ))}
              </ul>
            )}
            <button className="secondary" onClick={() => setHistorico(null)}>
              Fechar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
