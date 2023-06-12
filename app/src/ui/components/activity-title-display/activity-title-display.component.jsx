import React from "react";

import gastador from "../../../assets/statistics/gastador.svg";
import poupador from "../../../assets/statistics/poupador.svg";
import investidor from "../../../assets/statistics/investidor.svg";
import investidorMestre from "../../../assets/statistics/investidor-mestre.svg";
import placeholder from "../../../assets/statistics/placeholder.svg";
import trophy from "../../../assets/challenge-icon/trophy.svg";

import "./activity-title-display.style.css";
export function ActivityTitleDisplay({ title, courseName }) {
  function getStatusIcon() {
    switch (courseName) {
      case "GASTADOR":
        return gastador;
      case "POUPADOR":
        return poupador;
      case "INVESTIDOR":
        return investidor;
      case "INVESTIDOR MESTRE":
        return investidorMestre;
      case "DESAFIO":
        return trophy;
      default:
        return placeholder;
    }
  }

  return (
    <div className="activity-title-display--container">
      <img
        className="activity-title-display--icon"
        src={getStatusIcon()}
        alt="icon"
      />
      <div className="activity-title-display--title">{title}</div>
    </div>
  );
}
