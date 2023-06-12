import "./help-learn.style.css";
import { NavBar, ScreenContainer } from "../../components";
import gastador from "../../../assets/statistics/gastador.svg";
import poupador from "../../../assets/statistics/poupador.svg";
import investidor from "../../../assets/statistics/investidor.svg";
import investidorMestre from "../../../assets/statistics/investidor-mestre.svg";
import seta from "../../../assets/help-page-icons/seta.svg";
import money from "../../../assets/help-page-icons/money.svg";

export function HelpLearn() {
  return (
    <ScreenContainer>
      <div className="help-container container">
        <div className="help-title-container">
          <img className="help-title-medal" src={money} alt="" />
          <h1 className="help-title">Aprender</h1>
          <img className="help-title-medal" src={money} alt="" />
        </div>

        <p className="help-p help-p-main">
          Bem vindo ao Crescendo Bolso, onde aprenderá educação financeira
          enquanto se diverte com seus amigos.
        </p>
        <p className="help-p help-p-normal help-p-normal-recuo">
          Há 4 níveis disponíveis e em cada um novos temas para aprender e
          praticar.
        </p>
        <div className="help-nivel-order-container">
          <div className="help-nivel-container">
            <img src={gastador} alt="" />
            <p className="help-nivel-name">Gastador</p>
          </div>

          <img className="help-seta" src={seta} alt="seta" />

          <div className="help-nivel-container">
            <img src={poupador} alt="" />
            <p className="help-nivel-name">Poupador</p>
          </div>

          <img className="help-seta" src={seta} alt="seta" />

          <div className="help-nivel-container">
            <img src={investidor} alt="" />
            <p className="help-nivel-name">Investidor</p>
          </div>

          <img className="help-seta" src={seta} alt="seta" />

          <div className="help-nivel-container">
            <img
              className="help-nivel-investidor-mestre"
              src={investidorMestre}
              alt=""
            />
            <p className="help-nivel-name">Investidor Mestre</p>
          </div>
        </div>

        <p className="help-p help-p-normal help-p-normal-recuo">
          Cada nível é composto por atividades ao clicar em uma atividade você
          terá acesso a uma aula. Em seguida, você responderá 5 questões sobre o
          conteudo que aprendeu.
        </p>

        <p className="help-p help-p-main">
          Cada resposta certa vale: <span className="help-destaque">60 XP</span>
          .
        </p>

        <p className="help-p help-p-normal help-p-normal-recuo">
          Caso acerte 3 ou mais questões o tema será concluído e você
          desbloqueará a atividade seguinte.
        </p>

        <p className="help-p help-p-normal help-p-normal-recuo">
          Você pode refazer atividades concluidas o quanto quiser,
          <span className="help-sublinhado">
            {" "}
            mas cada questão certa vale 10 XP.
          </span>
        </p>
        <p className="help-p help-p-main">
          Avance nos cursos para melhorar seu título.
        </p>
        <p className="help-p help-p-message">
          Está pronto para virar um investidor mestre???
        </p>
      </div>
      <NavBar />
    </ScreenContainer>
  );
}
