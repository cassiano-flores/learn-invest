import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useParams, useLocation } from "react-router-dom";
import "./question.style.css";
import { answerPractice, detailActivity } from "../../../api";
import {
  Button,
  ScreenContainer,
  ScoreBoard,
  ActivityTitleDisplay,
} from "../../components";
import { useValidateAutentication } from "../../../hooks";

export function Question() {
  const LAST_QUESTION = 5;
  const ITS_NOT_THE_FIRST_TIME = 2;
  const { state } = useLocation();
  const activityId = useParams().activityId;
  const [isLoading, setIsLoading] = useState(true);
  const [currentQuestion, setCurrentQuestion] = useState(state.currentQuestion);
  const [hits, setHits] = useState(state.hits);
  const [countTimes, setCountTimes] = useState(0);
  const navigate = useNavigate();
  const { validateAutentication } = useValidateAutentication();
  const [title, setTitle] = useState("");
  const [courseName, setCourseName] = useState("");

  const { questions, ranker, isAnswering, IS_CHALLENGE } = state;

  async function handleClick(answer) {
    try {
      setIsLoading(true);
      const responseHit = await answerPractice(
        currentQuestion.content.id,
        answer
      );
      setHits((oldHits) => [...oldHits, responseHit]);
    } catch (error) {
      validateAutentication(error);
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {
    async function fecthData() {
      try {
        if (!IS_CHALLENGE) {
          const { title, courseName } = await detailActivity(activityId);
          setTitle(title);
          setCourseName(courseName);
        }
      } catch (error) {
        validateAutentication(error);
      } finally {
        setIsLoading(false);
      }
    }
    fecthData();
  }, []);

  useEffect(() => {
    if (countTimes >= ITS_NOT_THE_FIRST_TIME) {
      setCurrentQuestion((oldCurrentQuestion) => ({
        ...oldCurrentQuestion,
        question: oldCurrentQuestion.question + 1,
        content: { ...questions[hits.length] },
      }));
    } else {
      setCountTimes((oldCountTimes) => oldCountTimes + 1);
    }
  }, [hits]);

  useEffect(() => {
    if (countTimes >= ITS_NOT_THE_FIRST_TIME) {
      if (hits.length === LAST_QUESTION) {
        if (IS_CHALLENGE) {
          navigate(`/league/challenge/result`, {
            state: {
              questions,
              currentQuestion,
              hits,
              IS_CHALLENGE,
              ranker,
              isAnswering,
            },
          });
        } else {
          navigate(`/learn/activity/${activityId}/result`, {
            state: { questions, currentQuestion, hits },
          });
        }
      } else {
        if (IS_CHALLENGE) {
          navigate(`/league/challenge`, {
            state: {
              questions,
              currentQuestion,
              hits,
              IS_CHALLENGE,
              ranker,
              isAnswering,
            },
          });
        } else {
          navigate(`/learn/activity/${activityId}/practice`, {
            state: { questions, currentQuestion, hits },
          });
        }
      }
    } else {
      setCountTimes((oldCountTimes) => oldCountTimes + 1);
    }
  }, [currentQuestion, hits]);

  return (
    <ScreenContainer>
      <div className="question--container">
        <div className="question--content">
          {IS_CHALLENGE ? (
            <ActivityTitleDisplay title="DESAFIO" courseName="DESAFIO" />
          ) : (
            <ActivityTitleDisplay title={title} courseName={courseName} />
          )}

          <ScoreBoard hits={hits} isLoading={isLoading} />

          <div className="question--text-container">
            <p className="question--text">
              {currentQuestion.content.questionText}
            </p>
          </div>
          <div className="question--alternatives-buttons-container">
            <Button
              className="question--alternatives-button"
              type="button"
              onClick={() => handleClick("A")}
            >
              <div className="question--alternative-container">
                <p className="letter">A</p>
              </div>
              <p className="question--alternative">
                {currentQuestion.content.alternativeA}
              </p>
            </Button>
            <Button
              className="question--alternatives-button"
              type="button"
              onClick={() => handleClick("B")}
            >
              <div className="question--alternative-container">
                <p className="letter">B</p>
              </div>
              <p className="question--alternative">
                {currentQuestion.content.alternativeB}
              </p>
            </Button>
            <Button
              className="question--alternatives-button"
              type="button"
              onClick={() => handleClick("C")}
            >
              <div className="question--alternative-container">
                <p className="letter">C</p>
              </div>
              <p className="question--alternative">
                {currentQuestion.content.alternativeC}
              </p>
            </Button>
            <Button
              className="question--alternatives-button"
              type="button"
              onClick={() => handleClick("D")}
            >
              <div className="question--alternative-container">
                <p className="letter">D</p>
              </div>
              <p className="question--alternative">
                {currentQuestion.content.alternativeD}
              </p>
            </Button>
          </div>
        </div>
      </div>
    </ScreenContainer>
  );
}
