const API_URL = import.meta.env.VITE_API_URL || "/api";

async function http(path, options = {}) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    let message = `Erro ${response.status}`;
    try {
      const body = await response.json();
      message = body.message || body.erro || JSON.stringify(body);
    } catch {
      message = response.statusText || message;
    }
    throw new Error(message);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export const api = {
  equipamentos: {
    listar: (params = "") => http(`/equipamentos${params}`),
    buscar: (id) => http(`/equipamentos/${id}`),
    criar: (dados) => http("/equipamentos", { method: "POST", body: JSON.stringify(dados) }),
    atualizar: (id, dados) => http(`/equipamentos/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    remover: (id) => http(`/equipamentos/${id}`, { method: "DELETE" }),
  },
  colaboradores: {
    listar: () => http("/colaboradores"),
    buscar: (id) => http(`/colaboradores/${id}`),
    criar: (dados) => http("/colaboradores", { method: "POST", body: JSON.stringify(dados) }),
    emprestimos: (id) => http(`/colaboradores/${id}/emprestimos`),
  },
  emprestimos: {
    listar: (atrasados = false) => http(`/emprestimos${atrasados ? "?atrasados=true" : ""}`),
    buscar: (id) => http(`/emprestimos/${id}`),
    criar: (dados) => http("/emprestimos", { method: "POST", body: JSON.stringify(dados) }),
    devolver: (id) => http(`/emprestimos/${id}/devolucao`, { method: "PUT" }),
  },
};
