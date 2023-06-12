import React from "react";
import "./header-loging.style.css";
import icon from "../../../assets/main-icon/main-icon.svg";

export function HeaderLoging({ children }) {
  return (
    <header className="header-logging--container">
      <img src={icon} alt="icone" />
      <p className="header-logging--titulo-da-pagina">{children}</p>
    </header>
  );
}
