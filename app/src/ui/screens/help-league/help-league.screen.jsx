import { NavBar, ScreenContainer } from "../../components";
import "./help-league.style.css";
import challengeMedal from "../../../assets/help-page-icons/challenge-medal.svg";
import lose from "../../../assets/help-page-icons/lose.svg";
import win from "../../../assets/help-page-icons/win.svg";
import gold from "../../../assets/help-page-icons/gold.svg";
import crown from "../../../assets/help-page-icons/crown.svg";

export function HelpLeague() {
  return (
    <ScreenContainer>
      <div className="help-container container">
        <div className="help-title-container">
          <img className="help-title-medal" src={challengeMedal} alt="" />
          <h1 className="help-title">Liga</h1>
          <img className="help-title-medal" src={challengeMedal} alt="" />
        </div>

        <p className="help-p help-p-main">
          Aqui você poderá desafiar seus amigos para ver quem sabe mais sobre
          educação financeira.
        </p>
        <p className="help-p help-p-normal help-p-normal-recuo">
          Cada desafio é composto por 5 questões referentes as atividades já
          concluídas pelo jogador, caso não tenha concluído nenhuma atividade
          serão selecionadas questões de todas as atividades do nível Gastador.
        </p>
        <div className="help-p-win-container">
          <img src={win} alt="winner" />
          <p className="help-p help-p-normal">
            O vencedor é premiado com 10 pontos de liga e 50 moedas.
          </p>
        </div>

        <div className="help-p-lose-container">
          <img src={lose} alt="loser" />
          <p className="help-p help-p-normal">
            O derrotado perde 5 pontos de liga e não ganha moedas.
          </p>
        </div>

        <div className="help-p-lose-container">
          <img className="gold-icon" src={gold} alt="a tie" />
          <p className="help-p help-p-normal">
            Em caso de empate ambos jogadores ganham 10 moedas.
          </p>
        </div>

        <div className="help-last-p-container">
          <img src={crown} alt="crown" />
          <p className="help-p help-p-message">
            Estude, seja o primeiro no rank e desbloqueie todos os ícones!!!
          </p>
        </div>
      </div>
      <NavBar />
    </ScreenContainer>
  );
}
