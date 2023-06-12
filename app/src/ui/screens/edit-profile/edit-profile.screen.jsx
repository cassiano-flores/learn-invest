import "./edit-profile.style.css";
import {
  NavBar,
  Button,
  Loader,
  Input,
  ScreenContainer,
  IconInput,
} from "../../components";
import { useState, useEffect } from "react";

import useGlobalUser from "../../../context/usuario/usuario.context";
import { Link, useNavigate } from "react-router-dom";
import { useForm, useValidateAutentication } from "../../../hooks";

import { editProfile, logoutUser, listUserIcons } from "../../../api";

export function EditProfile() {
  const [user, setUser] = useGlobalUser();
  const [userIcons, setUserIcons] = useState([]);
  const [isLoading, setLoading] = useState(true);
  const [message, setMessage] = useState();
  const { validateAutentication } = useValidateAutentication();
  const TIME_TO_HIDE_MESSAGE = 3000;

  useEffect(() => {
    try {
      setLoading(true);
      async function fetchData() {
        const icons = await listUserIcons();
        setUserIcons(icons);
      }
      fetchData();
    } catch (error) {
      validateAutentication(error.response.status);
      setMessage("Não foi possível acessar os icones.");
    } finally {
      setLoading(false);
    }
  }, []);

  const navigate = useNavigate();
  const { formInput, setData } = useForm({
    name: user.name,
    nickname: user.nickname,
    iconId: user.currentIcon.id,
  });

  function handleChange(event) {
    setData(event.target);
  }

  async function handleLogout() {
    await logoutUser();
    setUser(null);
    localStorage.removeItem("user");
    navigate("/");
  }

  function handleInsertQuestion() {
    navigate("/profile/insert-question");
  }

  async function handleEditProfile() {
    try {
      await editProfile(formInput);
      setUser({
        ...user,
        currentIcon: {
          id: formInput.iconId,
        },
      });
      setMessage("Perfil editado com sucesso.");
      setTimeout(() => setMessage(""), TIME_TO_HIDE_MESSAGE);
    } catch (error) {
      error.response.status === 400
        ? setMessage(`${error.response.data.message}`)
        : setMessage("Erro ao conectar no servidor");
    }
  }

  function handleQuestionButton() {
    if (user.permissions.includes("ADMIN")) {
      return (
        <Button
          className="edit-profile--insert-question"
          onClick={handleInsertQuestion}
        >
          Adicionar Questão
        </Button>
      );
    }
  }

  return (
    <ScreenContainer>
      <div className="container edit-profile--container">
        <p className="store--title">Editar perfil</p>
        <form className="form-container">
          <div className="input-container input-container-padding">
            <Input
              label="NOME"
              name="name"
              onChange={handleChange}
              placeholder="Digite seu nome"
              value={formInput.name}
              required
            />
          </div>
          <div className="input-container input-container-padding">
            <Input
              label="APELIDO"
              name="nickname"
              onChange={handleChange}
              value={formInput.nickname}
              placeholder="Digite seu apelido"
            />
          </div>
          <div>
            <p className="input-label">Icones</p>
            <div className="edit-profile--available-icons-container">
              {isLoading ? (
                <Loader />
              ) : (
                <>
                  {userIcons.map((icon) => (
                    <IconInput
                      key={icon.id}
                      onChange={handleChange}
                      id={icon.id}
                      name={icon.name}
                      selected={formInput.iconId}
                    />
                  ))}
                </>
              )}
            </div>
          </div>
        </form>

        <div className="insert-question--message-container">
          <p className="insert-question--message">{message ?? message}</p>
        </div>

        <div className="edit-profile--menu-container">
          <Button onClick={handleEditProfile}>SALVAR</Button>
          {handleQuestionButton()}
          <Link onClick={handleLogout}>
            <p className="edit-profile--logout-button">DESCONECTAR</p>
          </Link>
        </div>
      </div>

      <NavBar />
    </ScreenContainer>
  );
}
