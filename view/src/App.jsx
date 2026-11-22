import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { Navbar } from "./components/Navbar";
import { Equipamentos } from "./pages/Equipamentos";
import { Colaboradores } from "./pages/Colaboradores";
import { Emprestimos } from "./pages/Emprestimos";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main>
        <Routes>
          <Route path="/" element={<Equipamentos />} />
          <Route path="/colaboradores" element={<Colaboradores />} />
          <Route path="/emprestimos" element={<Emprestimos />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
