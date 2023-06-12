import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import "./course.style.css";
import gastador from "../../../assets/statistics/gastador.svg";
import poupador from "../../../assets/statistics/poupador.svg";
import investidor from "../../../assets/statistics/investidor.svg";
import investidorMestre from "../../../assets/statistics/investidor-mestre.svg";
import placeholder from "../../../assets/statistics/placeholder.svg";
import openActivitieButton from "../../../assets/buttons/chevron-right.svg";

export function Course({ course, disable, finishedActivitiesId }) {
  const navigate = useNavigate();

  function getStatusIcon() {
    switch (course.name) {
      case "GASTADOR":
        return gastador;
      case "POUPADOR":
        return poupador;
      case "INVESTIDOR":
        return investidor;
      case "INVESTIDOR MESTRE":
        return investidorMestre;
      default:
        return placeholder;
    }
  }

  function renderCourseClassName() {
    return !disable ? "course--container" : "course--container-disabled";
  }

  function renderIsActivityEnded(activity) {
    return finishedActivitiesId.includes(activity?.id);
  }

  function handleEnableLink(activity) {
    if (!disable) {
      navigate(`/learn/activity/${activity.id}`);
    }
  }

  useEffect(() => {
    renderIsActivityEnded();
  }, [finishedActivitiesId]);

  return (
    <div className={`${renderCourseClassName()}`}>
      <div className="course--title-container">
        <div className="course--title-icon-label">
          <img src={getStatusIcon()} alt="" />
          <div>
            <p className="course--title-label">Nivel</p>
            <p className="course--title">{course.name}</p>
          </div>
        </div>
      </div>

      <div className="course--activities-container">
        {course.activities.map((activity) => (
          <div
            key={activity.id}
            className="course--activity-container"
            onClick={() => handleEnableLink(activity)}
          >
            <div>
              <p className="course--activity-title">{activity.title}</p>
              {renderIsActivityEnded(activity) ? (
                <p className="course--activity-status">Concluido</p>
              ) : (
                <p></p>
              )}
            </div>

            <div>
              <img src={openActivitieButton} alt="Abrir atividade" />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
