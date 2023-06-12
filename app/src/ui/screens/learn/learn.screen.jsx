import { useState, useEffect } from "react";
import "./learn.style.css";
import { listCourses, detailProfile } from "../../../api";
import { Course, NavBar, ScreenContainer, Loader } from "../../components";
import { useValidateAutentication } from "../../../hooks";
import { Link } from "react-router-dom";

export function Learn() {
  const [courses, setCourses] = useState([]);
  const [userCourses, setUserCourses] = useState([]);
  const [userCoursesId, setUserCoursesId] = useState([]);
  const [finishedActivities, setFinishedActivities] = useState([]);
  const [finishedActivitiesId, setFinishedActivitiesId] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const { validateAutentication } = useValidateAutentication();

  useEffect(() => {
    async function fetchData() {
      try {
        setIsLoading(true);
        const responseUser = await detailProfile();
        const responseCourses = await listCourses();

        setCourses((oldCourses) => [...oldCourses, ...responseCourses]);
        setUserCourses((oldUserCourses) => [
          ...oldUserCourses,
          ...responseUser.courses,
        ]);
        setFinishedActivities((oldFinishedActivities) => [
          ...oldFinishedActivities,
          ...responseUser.finishedActivities,
        ]);
      } catch (error) {
        validateAutentication(error.response.status);
      } finally {
        setIsLoading(false);
      }
    }
    fetchData();
  }, []);

  useEffect(() => {
    setUserCoursesId(userCourses.map(({ id }) => id));
  }, [userCourses]);

  useEffect(() => {
    setFinishedActivitiesId(finishedActivities.map(({ id }) => id));
  }, [finishedActivities]);

  return (
    <>
      <ScreenContainer>
        {isLoading ? (
          <div className="centralize-loader">
            <Loader />
          </div>
        ) : (
          <div className="container">
            <Link to="/learn/help" className="learn--help-button">
              ?
            </Link>
            <div className="learn--container">
              {courses.map((course) => {
                const disable = !userCoursesId.includes(course.id);
                return (
                  <Course
                    key={course.id}
                    course={course}
                    disable={disable}
                    finishedActivitiesId={finishedActivitiesId}
                  />
                );
              })}
            </div>
          </div>
        )}
        <NavBar />
      </ScreenContainer>
    </>
  );
}
