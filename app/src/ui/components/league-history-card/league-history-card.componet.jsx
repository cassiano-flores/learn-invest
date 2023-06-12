import "./league-history-card.style.css";
import { useEffect, useState } from "react";
import useGlobalUser from "../../../context/usuario/usuario.context";

export function LeagueHistoryCard({ card }) {
  const VITORIA = 2;
  const DERROTA = 0;
  const EMPATE = 1;
  const ABERTO = 3;
  const {
    challengedIn,
    currentUsuario,
    currentUsuarioScore,
    opponentUsuario,
    opponentUsuarioScore,
  } = card;
  const [resultIndex, setResultIndex] = useState(ABERTO);
  const [user] = useGlobalUser();
  const result = ["DERROTA", "EMPATE", "VITORIA", "ABERTO"];

  useEffect(() => {
    if (
      (!currentUsuarioScore || !opponentUsuarioScore) &&
      !(currentUsuarioScore === 0 || opponentUsuarioScore === 0)
    ) {
      setResultIndex(ABERTO);
    } else {
      if (user.id === currentUsuario.id) {
        if (currentUsuarioScore > opponentUsuarioScore) {
          setResultIndex(VITORIA);
        } else if (currentUsuarioScore < opponentUsuarioScore) {
          setResultIndex(DERROTA);
        } else if (opponentUsuarioScore === currentUsuarioScore) {
          setResultIndex(EMPATE);
        }
      } else {
        if (opponentUsuarioScore > currentUsuarioScore) {
          setResultIndex(VITORIA);
        } else if (opponentUsuarioScore < currentUsuarioScore) {
          setResultIndex(DERROTA);
        } else if (opponentUsuarioScore === currentUsuarioScore) {
          setResultIndex(EMPATE);
        }
      }
    }
  }, []);

  return (
    <div className="league-history-card--container">
      <div className="league-history-card--user-container">
        <img
          className="league-history-card--icon"
          src={`../../icons/${currentUsuario.currentIconId}.svg`}
          alt={`Ícone de usuário de ${currentUsuario.name}`}
        />
        <p className="league-history-card--user-name">
          {currentUsuario.nickname
            ? currentUsuario.nickname
            : currentUsuario.name}
        </p>
        <p
          className={`league-history-card--data league-history-card--result-${result[resultIndex]}`}
        >
          Acertos: {currentUsuarioScore}
        </p>
      </div>
      <div className="league-history-card--result-container">
        <p
          className={`league-history-card--result league-history-card--result-${result[resultIndex]}`}
        >
          {result[resultIndex]}
        </p>
        <div>
          <p
            className={`league-history-card--data league-history-card--result-${result[resultIndex]}`}
          >
            Enviado em:
          </p>
          <p
            className={`league-history-card--data league-history-card--result-${result[resultIndex]}`}
          >
            {challengedIn}
          </p>
        </div>
      </div>

      <div className="league-history-card--user-container">
        <img
          className="league-history-card--icon"
          src={`../../icons/${opponentUsuario.currentIconId}.svg`}
          alt={`Ícone de usuário de ${opponentUsuario.name}`}
        />
        <p className="league-history-card--user-name">
          {opponentUsuario.nickname
            ? opponentUsuario.nickname
            : opponentUsuario.name}
        </p>
        <p
          className={`league-history-card--data league-history-card--result-${result[resultIndex]}`}
        >
          Acertos: {opponentUsuarioScore}
        </p>
      </div>
    </div>
  );
}
