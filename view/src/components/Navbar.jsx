import { NavLink } from "react-router-dom";

export function Navbar() {
  return (
    <nav className="navbar">
      <h1>Empréstimo de Equipamentos</h1>
      <ul>
        <li>
          <NavLink to="/" end>
            Equipamentos
          </NavLink>
        </li>
        <li>
          <NavLink to="/colaboradores">Colaboradores</NavLink>
        </li>
        <li>
          <NavLink to="/emprestimos">Empréstimos</NavLink>
        </li>
      </ul>
    </nav>
  );
}
