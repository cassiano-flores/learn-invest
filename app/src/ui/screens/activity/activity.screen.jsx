import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useParams } from "react-router-dom";
import "./activity.style.css";
import { detailActivity, listPracticeQuestions } from "../../../api";
import {
  Button,
  ScreenContainer,
  NavBar,
  Loader,
  ActivityTitleDisplay,
} from "../../components";

export function Activity() {
  const activityId = useParams().activityId;
  const [activity, setActivity] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [currentQuestion, setCurrentQuestion] = useState({});
  const [hits] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchData() {
      setIsLoading(true);
      const response = await detailActivity(activityId);
      setActivity(response);

      const responseQuestions = await listPracticeQuestions(activityId);
      setQuestions((oldQuestions) => [...oldQuestions, ...responseQuestions]);
      setIsLoading(false);
    }
    fetchData();
  }, [activityId]);

  useEffect(() => {
    setCurrentQuestion({ question: 1, content: { ...questions[0] } });
  }, [questions]);

  function handleStart() {
    navigate(`/learn/activity/${activityId}/practice`, {
      state: { questions, currentQuestion, hits },
    });
  }

  return (
    <ScreenContainer>
      {isLoading ? (
        <div className="centralize-loader">
          <Loader />
        </div>
      ) : (
        <div className="activity--container-screen">
          <div className="activity--container">
            <div className="activity--content">
              <ActivityTitleDisplay
                title={activity.title}
                courseName={activity.courseName}
              />
              <div className="activity--lesson">{activity.lesson}</div>
              <div className="activity--button-container">
                <Button type="button" onClick={() => handleStart()}>
                  Iniciar prática
                </Button>
              </div>
            </div>
          </div>
        </div>
      )}

      <NavBar />
    </ScreenContainer>
  );
}
