import React from "react";
import "./score-board.style.css";
import positive from "../../../assets/buttons/accept.svg";
import negative from "../../../assets/buttons/cancel.svg";

export function ScoreBoard({ hits, isLoading }) {
  const NUMBER_OF_QUESTIONS = ["1", "2", "3", "4", "5"];
  const ONE = 1;

  function renderScoreItem(question, index) {
    if (index < hits.length) {
      if (hits[index]) {
        if (Number(question) === NUMBER_OF_QUESTIONS.length) {
          return (
            <div className="score-board--question-container">
              <img className="score-board-result" src={positive} alt="right" />
            </div>
          );
        }
        return (
          <div className="score-board--question-container">
            <img className="score-board-result" src={positive} alt="right" />
            <div className="score-board--progress-bar score-board--progress-bar-right"></div>
          </div>
        );
      } else {
        if (Number(question) === NUMBER_OF_QUESTIONS.length) {
          return (
            <div className="score-board--question-container">
              <img className="score-board-result" src={negative} alt="wrong" />
            </div>
          );
        }

        return (
          <div className="score-board--question-container">
            <img className="score-board-result" src={negative} alt="wrong" />
            <div className="score-board--progress-bar score-board--progress-bar-wrong"></div>
          </div>
        );
      }
    }
    if (Number(question) === hits.length + ONE) {
      return (
        <div className="score-board--question-container">
          <div className="score-board--number-container score-board--actual-question">
            {isLoading ? (
              <div className="score-board--loader"></div>
            ) : (
              <p>{question}</p>
            )}
          </div>

          {Number(question) !== ONE && (
            <div className="score-board--progress-bar score-board--progress-actual-question"></div>
          )}
        </div>
      );
    }
    return (
      <div className="score-board--question-container">
        <div className="score-board--number-container score-board--nexts-question">
          <p>{question}</p>
        </div>
        <div className="score-board--progress-bar score-board--progress-bar-non-answered"></div>
      </div>
    );
  }

  return (
    <div className="score-board">
      <p className="score-board--title">Progresso</p>
      <div className="score-board--container">
        {NUMBER_OF_QUESTIONS.map((question, index) => (
          <div className="score-board--item-container" key={index}>
            {renderScoreItem(question, index)}
          </div>
        ))}
      </div>
    </div>
  );
}
