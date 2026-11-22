import { useEffect, useState } from "react";
import { api } from "../api";

export function Emprestimos() {
  const [lista, setLista] = useState([]);
  const [equipamentos, setEquipamentos] = useState([]);
  const [colaboradores, setColaboradores] = useState([]);
  const [form, setForm] = useState({ equipamentoId: "", colaboradorId: "" });
  const [erro, setErro] = useState("");
  const [somenteAtrasados, setSomenteAtrasados] = useState(false);

  async function carregar() {
    try {
      const [emp, eqp, col] = await Promise.all([
        api.emprestimos.listar(somenteAtrasados),
        api.equipamentos.listar("?disponivel=true"),
        api.colaboradores.listar(),
      ]);
      setLista(emp);
      setEquipamentos(eqp);
      setColaboradores(col);
      setErro("");
    } catch (e) {
      setErro(e.message);
    }
  }

  useEffect(() => {
    carregar();
  }, [somenteAtrasados]);

  async function criar(e) {
    e.preventDefault();
    try {
      await api.emprestimos.criar({
        equipamentoId: Number(form.equipamentoId),
        colaboradorId: Number(form.colaboradorId),
      });
      setForm({ equipamentoId: "", colaboradorId: "" });
      await carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  async function devolver(id) {
    try {
      await api.emprestimos.devolver(id);
      await carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  return (
    <div className="page">
      <h2>Empréstimos</h2>

      {erro && <div className="erro">{erro}</div>}

      <form onSubmit={criar} className="form">
        <h3>Novo empréstimo</h3>
        <select
          value={form.equipamentoId}
          onChange={(e) => setForm({ ...form, equipamentoId: e.target.value })}
          required
        >
          <option value="">Selecione um equipamento</option>
          {equipamentos.map((eq) => (
            <option key={eq.id} value={eq.id}>
              {eq.nome} ({eq.patrimonio})
            </option>
          ))}
        </select>
        <select
          value={form.colaboradorId}
          onChange={(e) => setForm({ ...form, colaboradorId: e.target.value })}
          required
        >
          <option value="">Selecione um colaborador</option>
          {colaboradores.map((c) => (
            <option key={c.id} value={c.id}>
              {c.nome}
            </option>
          ))}
        </select>
        <button type="submit">Emprestar</button>
      </form>

      <div className="filtros">
        <label>
          <input
            type="checkbox"
            checked={somenteAtrasados}
            onChange={(e) => setSomenteAtrasados(e.target.checked)}
          />
          Só atrasados
        </label>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Equipamento</th>
            <th>Colaborador</th>
            <th>Retirada</th>
            <th>Prevista</th>
            <th>Devolução</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {lista.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.equipamento.nome}</td>
              <td>{item.colaborador.nome}</td>
              <td>{item.dataEmprestimo}</td>
              <td>{item.dataPrevistaDevolucao}</td>
              <td>{item.dataDevolucao || "-"}</td>
              <td>
                {item.dataDevolucao ? (
                  <span className="badge ok">Devolvido</span>
                ) : item.atrasado ? (
                  <span className="badge danger">Atrasado</span>
                ) : (
                  <span className="badge warning">Em andamento</span>
                )}
              </td>
              <td>
                {!item.dataDevolucao && (
                  <button className="small" onClick={() => devolver(item.id)}>
                    Devolver
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
