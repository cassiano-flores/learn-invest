import "./list-rankers.style.css";
import rank1 from "../../../assets/ranks/rank1.svg";
import rank2 from "../../../assets/ranks/rank2.svg";
import rank3 from "../../../assets/ranks/rank3.svg";

import { useState, useEffect } from "react";

import { Button } from "../../components";

import useGlobalUser from "../../../context/usuario/usuario.context";

import { useNavigate } from "react-router-dom";

import { listChallengeQuestions } from "../../../api";

export function ListRankers({ ranker, posicao, haveSent, haveReceived }) {
  const [user] = useGlobalUser();
  const navigate = useNavigate();
  const [currentQuestion, setCurrentQuestion] = useState({});
  const [questions, setQuestions] = useState([]);
  const [hits] = useState([]);
  const IS_CHALLENGE = true;
  const EXISTING_CHALLENGE = 1;

  const [isAnswering, setIsAnswering] = useState();

  function getMedal() {
    switch (posicao) {
      case 1:
        return rank1;
      case 2:
        return rank2;
      case 3:
        return rank3;
      default:
        return false;
    }
  }

  useEffect(() => {
    setCurrentQuestion({ question: 1, content: { ...questions[0] } });
  }, [questions]);

  useEffect(() => {
    if (questions.length !== 0) {
      navigate(`/league/challenge`, {
        state: {
          questions,
          currentQuestion,
          hits,
          IS_CHALLENGE,
          ranker,
          isAnswering,
        },
      });
    }
  }, [currentQuestion]);

  async function handleStartChallenge() {
    const response = await listChallengeQuestions();
    setQuestions(response);
    setIsAnswering(false);
  }

  async function handleReplyChallenge() {
    const response = await listChallengeQuestions();
    setQuestions(response);
    setIsAnswering(true);
  }

  function renderButton() {
    if (user.id === ranker.id) {
      return <p className="list-rankers--message">Eu</p>;
    }
    if (haveReceived.length === EXISTING_CHALLENGE) {
      return (
        <Button
          onClick={handleReplyChallenge}
          className="list-rankers--challenge-button"
        >
          Responder
        </Button>
      );
    }
    if (haveSent.length === EXISTING_CHALLENGE) {
      return <p className="list-rankers--message">Enviado</p>;
    }

    return (
      <Button
        onClick={handleStartChallenge}
        className="list-rankers--challenge-button"
      >
        Desafiar
      </Button>
    );
  }

  return (
    <div className="list-rankers--ranker-container">
      {getMedal() ? (
        <img className="list-rankers--ranker-medal" src={getMedal()} alt="" />
      ) : (
        <div className="list-rankers--position-container">
          <p className="list-rankers--position">{posicao}º</p>
        </div>
      )}

      <div className="list-rankers--img-nick-point-button">
        <div className="list-rankers--ranker-data">
          <img
            src={`../../../icons/${ranker.currentIcon.id}.svg`}
            alt={`ícone de ${ranker.name}`}
            className="list-rankers--icon-img"
          />
          <div>
            <p className="list-rankers--ranker-name">
              {ranker.nickname ? ranker.nickname : ranker.name}
            </p>
            <p className="list-rankers--ranker-pdl">
              {ranker.leaguePoints} PDL
            </p>
          </div>
        </div>

        {renderButton()}
      </div>
    </div>
  );
}
