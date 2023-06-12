import React from "react";
import "./header-stats.style.css";
import { StatusDisplay } from "../../components";

export function HeaderStats({ iconId, coins, xp, leagueTitle }) {
  return (
    <header className="headerstats--container">
      <div className="headerstats--background"></div>
      <img
        className="headerstats--icon-container"
        src={`../../../icons/${iconId}.svg`}
        alt=""
      />
      <div className="headerstats--scale-status">
        <StatusDisplay status={leagueTitle} valor={`${xp} XP`} />
      </div>
      <div className="headerstats--scale-status">
        <StatusDisplay status="MOEDAS" valor={coins} />
      </div>
    </header>
  );
}
