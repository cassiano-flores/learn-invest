import React from "react";
import { NavLink } from "react-router-dom";

import liga from "../../../assets/nav-bar/liga.svg";
import cursos from "../../../assets/nav-bar/cursos.svg";
import loja from "../../../assets/nav-bar/loja.svg";
import perfil from "../../../assets/nav-bar/perfil.svg";

import "./nav-bar.style.css";

export function NavBar() {
  const selected = "navbar--item-selected-container";
  const normal = "navbar--item-container";

  return (
    <nav className="navbar-container">
      <NavLink
        to="/learn"
        className={({ isActive }) => (isActive ? selected : normal)}
      >
        <img className="navbar--item" src={cursos} alt="Ícone de lista, é o botão que direciona para página aprender" />
      </NavLink>

      <NavLink
        to="/league"
        className={({ isActive }) => (isActive ? selected : normal)}
      >
        <img className="navbar--item" src={liga} alt="Ícone de troféu, é o botão que direciona para página da liga" />
      </NavLink>
      <NavLink
        to="/store"
        className={({ isActive }) => (isActive ? selected : normal)}
      >
        <img className="navbar--item" src={loja} alt="Ícone de carrinho de compra, é o botão que direciona para página da loja" />
      </NavLink>
      <NavLink
        to="/profile"
        className={({ isActive }) => (isActive ? selected : normal)}
      >
        <img className="navbar--item" src={perfil} alt="[Icone da silhueta de um busto, é o botão que direciona para página de perfil" />
      </NavLink>
    </nav>
  );
}
