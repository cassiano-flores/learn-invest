import { Button, Input, HeaderLoging } from "../../components";
import { useEffect, useState } from "react";
import { resetPassword } from "../../../api";
import { useSearchParams } from "react-router-dom";

export function ResetPassword() {
  const [formInput, setFormInput] = useState({
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState();
  const [sucess, setSucess] = useState();
  const [apiData, setApiData] = useState(null);
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const TIME_TO_HIDE_MESSAGE = 3000;

  function handleChange(event) {
    const { name, value } = event.target;

    setFormInput((oldFormInput) => ({ ...oldFormInput, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (formInput.password === formInput.confirmPassword) {
      try {
        const sendMail = await resetPassword(
          {
            password: formInput.password,
          },
          token
        );
        setApiData(sendMail);
      } catch (error) {
        setError("Não foi possível alterar sua senha.");
        setTimeout(() => {
          setError("");
        }, TIME_TO_HIDE_MESSAGE);
      }
    } else {
      setError("Digite a senha corretamente.");
      setTimeout(() => {
        setError("");
      }, TIME_TO_HIDE_MESSAGE);
    }
  }

  useEffect(() => {
    if (apiData != null) {
      setSucess("Senha alterada com sucesso!");
    }
  }, [apiData]);
  return (
    <div className="forgot-password--container">
      <HeaderLoging>Esqueci minha senha</HeaderLoging>

      <div className="forgot-password">
        <form className="form-container" onSubmit={handleSubmit}>
          <div className="inputs-container">
            <div className="input-container">
              <Input
                label="Nova senha:"
                name="password"
                onChange={handleChange}
                placeholder="Digite sua nova senha aqui"
              />
            </div>

            <div className="input-container">
              <Input
                label="Confirmar nova senha:"
                name="confirmPassword"
                onChange={handleChange}
                placeholder="Confirme sua senha"
              />
            </div>
          </div>

          <div className="login--message-container">
            {error && <p className="login--message">{error}</p>}
            {sucess && <p className="login--message">{sucess}</p>}
          </div>

          <Button>Enviar</Button>
        </form>
      </div>
    </div>
  );
}
