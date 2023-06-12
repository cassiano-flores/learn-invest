import React from "react";
import "./icon-input.style.css";
export function IconInput({ onChange, id, name, selected }) {
  return (
    <label className="icon-input--container">
      <input
        className="hide"
        type="radio"
        name="iconId"
        value={id}
        onChange={onChange}
        checked={Number(selected) === id}
      />
      <div className="icon-input--icon-container">
        <img
          className="icon-input--icon"
          src={`../../icons/${id}.svg`}
          alt=""
        />
      </div>
      <p className="icon-input--icon-name">{name}</p>
    </label>
  );
}
