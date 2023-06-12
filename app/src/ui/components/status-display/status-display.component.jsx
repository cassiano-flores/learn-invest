import React from "react";
import "./status-display.style.css";

import gastador from "../../../assets/statistics/gastador.svg";
import poupador from "../../../assets/statistics/poupador.svg";
import investidor from "../../../assets/statistics/investidor.svg";
import investidorMestre from "../../../assets/statistics/investidor-mestre.svg";
import placeholder from "../../../assets/statistics/placeholder.svg";
import coins from "../../../assets/coin/coins.svg";

export function StatusDisplay({ status, valor }) {
  function getStatusIcon() {
    switch (status) {
      case "GASTADOR":
        return gastador;
      case "POUPADOR":
        return poupador;
      case "INVESTIDOR":
        return investidor;
      case "INVESTIDOR_MESTRE":
        return investidorMestre;
      case "MOEDAS":
        return coins;
      default:
        return placeholder;
    }
  }
  return (
    <div className="status-display--container">
      <p className="status-display--status-name">
        {status.split("_").join(" ")}
      </p>
      <div className="status-display--status-container">
        <img
          className="status-display--icon"
          src={getStatusIcon()}
          alt={status}
        />
        <p className="status-display--status-value">{valor}</p>
      </div>
    </div>
  );
}
