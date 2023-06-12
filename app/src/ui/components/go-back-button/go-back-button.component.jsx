import "./go-back-button.style.css";
import { useNavigate } from "react-router-dom";
import backIcon from "../../../assets/buttons/close.svg";

export function GoBackButton() {
  const navigate = useNavigate();

  return (
    <button className="back-button" onClick={() => navigate(-1)}>
      <img
        src={backIcon}
        alt="icone de seta apontando para a esquerda, indicando voltar"
      />
    </button>
  );
}
