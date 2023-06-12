import React from "react";
import "./loader.style.css";
import icon from "../../../assets/loader/loader-image.svg";
export const Loader = ({ small }) => {
  return (
    <div className="flip" id="card">
      <div className={small ? "face-small" : "face"}>
        <img className="loader-icon" src={icon} alt="Carregando ..." />
      </div>
    </div>
  );
};
