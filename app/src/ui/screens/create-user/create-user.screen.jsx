import "./create-user.style.css";
import { useEffect, useState } from "react";
import { loginUser, registerUser } from "../../../api/index";
import { Button, Input, GoBackButton, HeaderLoging } from "../../components";
import { useNavigate } from "react-router-dom";
import useGlobalUser from "../../../context/usuario/usuario.context";

export function CreateUser() {
  const [formInput, setFormInput] = useState({
    nome: " ",
    apelido: " ",
    email: " ",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState();
  const [user, setUser] = useGlobalUser();

  const navigate = useNavigate();

  function handleChange(event) {
    const { name, value } = event.target;

    setFormInput((oldFormInput) => ({ ...oldFormInput, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (!formInput.email || !formInput.password || !formInput.name) {
      setError("Preencha os campos corretamente.");
    } else {
      if (formInput.password === formInput.confirmPassword) {
        try {
          await registerUser({
            name: formInput.name,
            nickname: formInput.nickname,
            email: formInput.email,
            password: formInput.password,
          });
          const user = await loginUser({
            username: formInput.email,
            password: formInput.password,
          });
          setUser(user);
        } catch (error) {
          error.response.status !== 500
            ? setError(error.response.data.message)
            : setError("Erro ao conectar ao servidor");
        }
      } else {
        setError("Confirme a senha corretamente");
      }
    }
  }

  useEffect(() => {
    if (user) {
      navigate("/profile");
    }
  }, [user]);

  return (
    <div className="create-container">
      <div className="create--go-back-container">
        <GoBackButton />
      </div>
      <HeaderLoging>Criar conta</HeaderLoging>
      <div className="create">
        <form className="form-container" onSubmit={handleSubmit}>
          <div className="inputs-container">
            <div className="input-container">
              <Input
                label="Nome:"
                name="name"
                onChange={handleChange}
                autoComplete="on"
                placeholder="Digite seu nome aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="Apelido (opcional):"
                name="nickname"
                onChange={handleChange}
                autoComplete="on"
                placeholder="Digite seu apelido aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="E-mail:"
                name="email"
                onChange={handleChange}
                type="email"
                autoComplete="on"
                placeholder="Digite seu e-mail aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="Senha:"
                name="password"
                onChange={handleChange}
                type="password"
                autoComplete="off"
                placeholder="Digite sua senha aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="Confirmar senha:"
                name="confirmPassword"
                onChange={handleChange}
                type="password"
                autoComplete="off"
                placeholder="Confirme sua senha"
              />
            </div>
          </div>

          <div className="login--message-container">
            {error && <p className="login--message">{error}</p>}
          </div>

          <Button type="submit">Criar Usuário</Button>
        </form>
      </div>
    </div>
  );
}
