import { Button, GoBackButton, Input, HeaderLoging } from "../../components";
import { useEffect, useState } from "react";
import "./forgot-password.style.css";
import { forgotPassword } from "../../../api";

export function ForgotPassword() {
  const [formInput, setFormInput] = useState({ email: "" });
  const [error, setError] = useState();
  const [sucess, setSucess] = useState();
  const [apiData, setApiData] = useState(null);
  const TIME_TO_HIDE_MESSAGE = 3000;
  function handleChange(event) {
    const { name, value } = event.target;

    setFormInput((oldFormInput) => ({ ...oldFormInput, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setSucess("Enviando ...");

    try {
      const sendMail = await forgotPassword({
        email: formInput.email,
      });
      setApiData(sendMail);
    } catch (error) {
      setSucess("");
      setError("E-mail não encontrado!");
      setTimeout(() => {
        setError("");
      }, TIME_TO_HIDE_MESSAGE);
    }
  }

  useEffect(() => {
    if (apiData != null) {
      setSucess("E-mail enviado com sucesso.");
    }
  }, [apiData]);

  return (
    <div className="forgot-password--container">
      <HeaderLoging>Esqueci minha senha</HeaderLoging>
      <div className="create--go-back-container">
        <GoBackButton />
      </div>
      <div className="forgot-password--message-container">
        <p className="login--message">
          Digite abaixo o seu e-mail para recuperar a senha de acesso à
          plataforma
        </p>
      </div>
      <div className="forgot-password">
        <form className="form-container" onSubmit={handleSubmit}>
          <div className="input-container">
            <Input
              label="E-mail:"
              name="email"
              value={formInput.email}
              onChange={handleChange}
              autoComplete="on"
              placeholder="Digite seu email aqui"
            />
          </div>

          <div className="login--message-container">
            {error && <p className="login--message">{error}</p>}
            {sucess && <p className="login--message">{sucess}</p>}
          </div>

          <Button>Recuperar senha</Button>
        </form>
      </div>
    </div>
  );
}
