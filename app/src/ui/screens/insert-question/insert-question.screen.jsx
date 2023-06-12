import "./insert-question.style.css";
import { NavBar, Button, ScreenContainer, Input } from "../../components";
import { useState, useEffect } from "react";
import { useForm, useValidateAutentication } from "../../../hooks";
import {
  includeQuestion,
  listCourseActivities,
  listCourses,
} from "../../../api";

export function InsertQuestion() {
  const { formInput, setData } = useForm({});
  const [courses, setCourses] = useState([]);
  const [activities, setActivities] = useState([]);
  const { validateAutentication } = useValidateAutentication();
  const [message, setMessage] = useState();
  function handleChange(event) {
    setData(event.target);
  }

  const NUMERO_DE_CAMPOS_FORMULARIO = 8;
  const TEMPO = 5000;

  async function submitQuestion() {
    try {
      await includeQuestion(formInput);
      setMessage("A questão foi adicionada com sucesso.");
      setTimeout(() => setMessage(""), TEMPO);
    } catch (error) {
      validateAutentication(error.response.status);
      setMessage(error.response.data.message);
    }
  }

  function handleSubmit(event) {
    event.preventDefault();

    const inputsArray = Object.keys(formInput);

    if (inputsArray.length !== NUMERO_DE_CAMPOS_FORMULARIO) {
      setMessage("Preencha todos os campos.");
    } else {
      submitQuestion();
    }
  }

  function handleChangeActivities(event) {
    setData(event.target);
  }

  useEffect(() => {
    async function fetchCourses() {
      try {
        const courses = await listCourses();
        setCourses(courses);
      } catch (error) {
        validateAutentication(error.response.status);
      }
    }

    fetchCourses();
  }, []);

  useEffect(() => {
    async function fetchActivities() {
      if (formInput?.nivel) {
        const activities = await listCourseActivities(formInput.nivel);
        setActivities(activities);
      }
    }
    try {
      fetchActivities();
    } catch (error) {
      validateAutentication(error.response.status);
    }
  }, [formInput]);

  return (
    <ScreenContainer>
      <div className="container insert-question--container">
        <p className="store--title">Adicionar Questão</p>
        <div className="input-container input-container-padding">
          <label className="insert-question--select">
            <p className="input-label">Nivel</p>
            <select
              className="insert-question--nivel-input input"
              onChange={handleChangeActivities}
              name="nivel"
              defaultValue=""
            >
              <option disabled value="">
                Selecione ...
              </option>
              {courses.map((course, index) => (
                <option value={course.id} key={index}>
                  {course.name}
                </option>
              ))}
            </select>
          </label>
        </div>

        <form className="insert-question--container" onSubmit={handleSubmit}>
          <div className="input-container input-container-padding">
            <label className="insert-question--select">
              <p className="input-label">Atividades</p>
              <select
                className="insert-question--nivel-input input"
                onChange={handleChangeActivities}
                name="activityId"
                defaultValue=""
              >
                <option disabled value="">
                  Selecione ...
                </option>
                {activities.map((activity, index) => (
                  <option className="options" value={activity.id} key={index}>
                    {activity.title}
                  </option>
                ))}
              </select>
            </label>
          </div>
          <div className="input-container input-container-padding">
            <label>
              <p className="input-label">Enunciado</p>
              <textarea
                rows="5"
                className="input-dado insert-question--text-area"
                onChange={handleChange}
                name="questionText"
                placeholder="Digite aqui o enunciado da questão"
              ></textarea>
            </label>
          </div>

          <div className="input-container input-container-padding">
            <p className="input-label">Resposta</p>
            <div className="insert-question--answer-container">
              <label>
                <input
                  className="hide"
                  type="radio"
                  name="answer"
                  id=""
                  value="A"
                  onChange={handleChange}
                />
                <p className="insert-question--answer">A</p>
              </label>
              <label>
                <input
                  className="hide"
                  type="radio"
                  name="answer"
                  id=""
                  value="B"
                  onChange={handleChange}
                />
                <p className="insert-question--answer">B</p>
              </label>
              <label>
                <input
                  className="hide"
                  type="radio"
                  name="answer"
                  id=""
                  value="C"
                  onChange={handleChange}
                />
                <p className="insert-question--answer">C</p>
              </label>
              <label>
                <input
                  className="hide"
                  type="radio"
                  name="answer"
                  id=""
                  value="D"
                  onChange={handleChange}
                />
                <p className="insert-question--answer">D</p>
              </label>
            </div>
          </div>
          <div className="input-container input-container-padding">
            <Input
              label="Alternativa A:"
              name="alternativeA"
              onChange={handleChange}
              placeholder="Digite a alternativa A aqui"
            />
          </div>

          <div className="input-container input-container-padding">
            <Input
              label="Alternativa B:"
              name="alternativeB"
              onChange={handleChange}
              placeholder="Digite a alternativa B aqui"
            />
          </div>

          <div className="input-container input-container-padding">
            <Input
              label="Alternativa C:"
              name="alternativeC"
              onChange={handleChange}
              placeholder="Digite a alternativa C aqui"
            />
          </div>

          <div className="input-container input-container-padding">
            <Input
              label="Alternativa D:"
              name="alternativeD"
              onChange={handleChange}
              placeholder="Digite a alternativa D aqui"
            />
          </div>

          <div className="insert-question--message-container">
            <p className="insert-question--message">{message ?? message}</p>
          </div>

          <Button>Salvar</Button>
        </form>
      </div>
      <NavBar />
    </ScreenContainer>
  );
}
