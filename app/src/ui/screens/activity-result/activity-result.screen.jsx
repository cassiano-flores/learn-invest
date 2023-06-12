import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useParams, useLocation } from "react-router-dom";
import "./activity-result.style.css";
import { finishActivity } from "../../../api";
import { Button, ScreenContainer, ScoreBoard } from "../../components";
import tier1 from "../../../assets/tiers/tier1.svg";
import tier2 from "../../../assets/tiers/tier2.svg";
import tier3 from "../../../assets/tiers/tier3.svg";

export function ActivityResult() {
  const QUANTITY_TO_PASS = 3;
  const PERFECT_SCORE = 5;
  const ALL_RIGHT_SCREEN = 2;
  const PASSED_SCREEN = 1;
  const NOT_PASSED_SCREEN = 0;
  const { state } = useLocation();
  const activityId = useParams().activityId;

  const [hits] = useState(state.hits);
  const [tier, setTier] = useState();
  const navigate = useNavigate();
  const [hitsCount, setHitsCount] = useState(0);

  const mainMessage = [
    "FOI QUASE, VIU?",
    "QUASE TUDO CERTO!",
    "QUE ORGULHO, HEIN?!",
  ];
  const tierIcons = [tier3, tier2, tier1];
  const message = [
    "Ooops! Não foi dessa fez, você precisa de 3 acertos para concluir a atividade.",
    "Oba! Você concluiu a atividade. Será que consegue acertar todas?",
    "Incrível! Você acertou tudo parabéns!",
  ];

  function hitsToInt() {
    return hits.reduce((count, value) => count + (value === true ? 1 : 0), 0);
  }

  function handleClickSucess() {
    navigate(`/learn`);
  }

  async function finishActivityResult() {
    await finishActivity(activityId, hitsCount);
  }

  function handleClickFail() {
    navigate(`/learn/activity/${activityId}`);
  }

  function handleClickClose() {
    navigate("/learn");
  }

  useEffect(() => {
    const points = hitsToInt();
    setHitsCount(points);
    if (PERFECT_SCORE === points) {
      setTier(ALL_RIGHT_SCREEN);
      finishActivityResult();
    } else if (QUANTITY_TO_PASS <= points) {
      setTier(PASSED_SCREEN);
      finishActivityResult();
    } else {
      setTier(NOT_PASSED_SCREEN);
    }
  }, []);

  return (
    <ScreenContainer>
      <div className="result--container">
        <p className="result--main-massage">{mainMessage[tier]}</p>
        <div className="result--image-container">
          <img src={tierIcons[tier]} alt="classificação" />
        </div>

        <ScoreBoard hits={hits} />
        <p className="result--massage">{message[tier]}</p>
        <div className="result--buttons-container">
          {tier === NOT_PASSED_SCREEN ? (
            <>
              <Button
                onClick={handleClickClose}
                className="result--close-button"
              >
                Fechar
              </Button>
              <Button onClick={handleClickFail}>Reler a aula</Button>
            </>
          ) : (
            <Button onClick={handleClickSucess}>Ok, Entendi.</Button>
          )}
        </div>
      </div>
    </ScreenContainer>
  );
}
