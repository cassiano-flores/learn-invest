import "./login.style.css";
import { Button, Input, HeaderLoging } from "../../components";
import { useEffect, useState } from "react";

import { useNavigate } from "react-router";
import { loginUser } from "../../../api/index";
import useGlobalUser from "../../../context/usuario/usuario.context";
import { useForm } from "../../../hooks";
import { Link } from "react-router-dom";

export function Login() {
  const { formInput, setData } = useForm({ email: "", password: "" });
  const [error, setError] = useState();
  const navigate = useNavigate();
  const [user, setUser] = useGlobalUser();

  function handleChange(event) {
    setData(event.target);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!formInput.email || !formInput.password) {
      setError("E-mail e senha são obrigatórios.");
    } else {
      try {
        const user = await loginUser({
          username: formInput.email,
          password: formInput.password,
        });
        setUser(user);
      } catch (error) {
        error.response.status === 401
          ? setError("Email ou senha inválidos.")
          : setError("Erro ao conectar no servidor.");
      }
    }
  }

  useEffect(() => {
    if (user) {
      navigate("/profile");
    }
  }, [user]);

  function handleCreateAccount() {
    navigate("/create");
  }

  return (
    <div className="login--container">
      <HeaderLoging>LOGIN</HeaderLoging>
      <div className="login">
        <form className="form-container" onSubmit={handleSubmit}>
          <div className="inputs-container">
            <div className="input-container">
              <Input
                label="E-mail:"
                name="email"
                value={formInput.email}
                onChange={handleChange}
                autoComplete="on"
                placeholder="Digite seu e-mail aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="Senha:"
                name="password"
                value={formInput.password}
                onChange={handleChange}
                type="password"
                autoComplete="off"
                placeholder="Digite sua senha aqui"
              />
            </div>
          </div>

          <div className="login--message-container">
            {error && <p className="login--message">{error}</p>}
          </div>

          <Button type="submit">Entrar</Button>
          <div className="login--message-container">
            <Link className="login--message" to="/forgot-password">
              Esqueceu a senha?
            </Link>
          </div>
        </form>
      </div>
      <Button onClick={handleCreateAccount} className="crate-button">
        Criar conta
      </Button>
    </div>
  );
}
