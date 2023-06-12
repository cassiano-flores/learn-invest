import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useLocation } from "react-router-dom";
import { sendChallenge, replyChallenge, resultChallenge } from "../../../api";
import { Button, ScreenContainer, ScoreBoard } from "../../components";
import challenge from "../../../assets/tiers/challenge.svg";
import { useValidateAutentication } from "../../../hooks";

export function ChallengeResult() {
  const { state } = useLocation();
  const [screen, setScreen] = useState();

  const [hits] = useState(state.hits);
  const navigate = useNavigate();

  const SEND_CHALLENGE_SCREEN = 0;
  const REPLAY_CHALLENGE_SCREEN = 1;
  const ERROR = 2;

  const { validateAutentication } = useValidateAutentication();

  const mainMessage = [
    "DESAFIO, FOI ENVIADO!",
    "DESAFIO FOI RESPONDIDO.",
    "OOOoopppsss!",
  ];

  const message = [
    "Você enviou um desafio, agora deve esperar ele ser respondido.",
    "O resultado pode ser visto no seu histórico de desafios",
    "Parece que a outra pessoa enviou um desafio primeiro.",
  ];

  function hitsToInt() {
    return hits.reduce((count, value) => count + (value === true ? 1 : 0), 0);
  }

  async function handleHistoryClick() {
    navigate(`/league/history`);
  }

  async function handleClickClose() {
    navigate("/league");
  }

  useEffect(() => {
    const score = hitsToInt();

    if (state.isAnswering) {
      async function replyChallengeResult() {
        try {
          const challengeId = await replyChallenge(state.ranker.id, score);
          await resultChallenge(challengeId);
          setScreen(REPLAY_CHALLENGE_SCREEN);
        } catch (error) {
          validateAutentication(error.response.status);
        }
      }

      replyChallengeResult();
    } else {
      async function sendChallengeResult() {
        try {
          await sendChallenge(state.ranker.id, score);
          setScreen(SEND_CHALLENGE_SCREEN);
        } catch (error) {
          if (error.response.status === 409) {
            setScreen(ERROR);
          }
          validateAutentication(error.response.status);
        }
      }
      sendChallengeResult();
    }
  }, []);

  return (
    <ScreenContainer>
      <div className="result--container">
        <p className="result--main-massage">{mainMessage[screen]}</p>
        <div className="result--image-container">
          <img src={challenge} alt="challenge-icon" />
        </div>

        <ScoreBoard hits={hits} />
        <p className="result--massage">{message[screen]}</p>
        <div className="result--buttons-container">
          {state.isAnswering ? (
            <>
              <Button
                onClick={handleClickClose}
                className="result--close-button"
              >
                Fechar
              </Button>
              <Button onClick={handleHistoryClick}>Histórico</Button>
            </>
          ) : (
            <Button className="result--close-button" onClick={handleClickClose}>
              Fechar
            </Button>
          )}
        </div>
      </div>
    </ScreenContainer>
  );
}
