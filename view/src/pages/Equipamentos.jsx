import { useEffect, useState } from "react";
import { api } from "../api";

const TIPOS = ["NOTEBOOK", "MONITOR", "MOUSE", "TECLADO", "HEADSET", "OUTRO"];

export function Equipamentos() {
  const [lista, setLista] = useState([]);
  const [form, setForm] = useState({ patrimonio: "", nome: "", tipo: "NOTEBOOK" });
  const [editando, setEditando] = useState(null);
  const [erro, setErro] = useState("");
  const [filtroTipo, setFiltroTipo] = useState("");
  const [somenteDisponivel, setSomenteDisponivel] = useState(false);

  async function carregar() {
    try {
      const params = new URLSearchParams();
      if (filtroTipo) params.append("tipo", filtroTipo);
      if (somenteDisponivel) params.append("disponivel", "true");
      const query = params.toString() ? `?${params.toString()}` : "";
      const dados = await api.equipamentos.listar(query);
      setLista(dados);
      setErro("");
    } catch (e) {
      setErro(e.message);
    }
  }

  useEffect(() => {
    carregar();
  }, [filtroTipo, somenteDisponivel]);

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function salvar(e) {
    e.preventDefault();
    try {
      if (editando) {
        await api.equipamentos.atualizar(editando, form);
      } else {
        await api.equipamentos.criar(form);
      }
      setForm({ patrimonio: "", nome: "", tipo: "NOTEBOOK" });
      setEditando(null);
      await carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  function iniciarEdicao(item) {
    setForm({ patrimonio: item.patrimonio, nome: item.nome, tipo: item.tipo });
    setEditando(item.id);
  }

  function cancelarEdicao() {
    setForm({ patrimonio: "", nome: "", tipo: "NOTEBOOK" });
    setEditando(null);
  }

  async function remover(id) {
    if (!confirm("Tem certeza que deseja remover este equipamento?")) return;
    try {
      await api.equipamentos.remover(id);
      await carregar();
    } catch (e) {
      setErro(e.message);
    }
  }

  return (
    <div className="page">
      <h2>Equipamentos</h2>

      {erro && <div className="erro">{erro}</div>}

      <form onSubmit={salvar} className="form">
        <h3>{editando ? "Editar equipamento" : "Novo equipamento"}</h3>
        <input
          name="patrimonio"
          placeholder="Patrimônio"
          value={form.patrimonio}
          onChange={handleChange}
          required
        />
        <input
          name="nome"
          placeholder="Nome"
          value={form.nome}
          onChange={handleChange}
          required
        />
        <select name="tipo" value={form.tipo} onChange={handleChange} required>
          {TIPOS.map((t) => (
            <option key={t} value={t}>
              {t}
            </option>
          ))}
        </select>
        <div className="form-actions">
          <button type="submit">{editando ? "Salvar" : "Cadastrar"}</button>
          {editando && (
            <button type="button" className="secondary" onClick={cancelarEdicao}>
              Cancelar
            </button>
          )}
        </div>
      </form>

      <div className="filtros">
        <select value={filtroTipo} onChange={(e) => setFiltroTipo(e.target.value)}>
          <option value="">Todos os tipos</option>
          {TIPOS.map((t) => (
            <option key={t} value={t}>
              {t}
            </option>
          ))}
        </select>
        <label>
          <input
            type="checkbox"
            checked={somenteDisponivel}
            onChange={(e) => setSomenteDisponivel(e.target.checked)}
          />
          Só disponíveis
        </label>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Patrimônio</th>
            <th>Nome</th>
            <th>Tipo</th>
            <th>Disponível</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {lista.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.patrimonio}</td>
              <td>{item.nome}</td>
              <td>{item.tipo}</td>
              <td>{item.disponivel ? "Sim" : "Não"}</td>
              <td>
                <button className="small" onClick={() => iniciarEdicao(item)}>
                  Editar
                </button>
                <button className="small danger" onClick={() => remover(item.id)}>
                  Remover
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
