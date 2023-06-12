import React, { useEffect, useState } from "react";
import { userAchievements } from "../../../api";

import "./achievement.style.css";
export function Achievement() {
  const PAGE_SIZE = 10;
  const PAGE_NUMBER = 0;
  const [achievements, setAchievements] = useState([]);

  useEffect(() => {
    async function fetchAchievements() {
      const achievements = await userAchievements(PAGE_SIZE, PAGE_NUMBER);
      setAchievements(achievements);
    }
    fetchAchievements();
  }, []);

  return (
    <div className="achievement-list--container">
      {achievements.map((achievement, index) => (
        <div key={index} className="achievement--container">
          <div className="achievement--image-profile-container">
            <p className="achievement--titulo achievement--text">
              {achievement.name}
            </p>
            <div className="achievement--content">
              <div>
                <img
                  className="achievement--image-profile"
                  src={`../../../../achievements/${achievement.icon}`}
                  alt={`Ícone do achievement ${achievement.name}`}
                />
              </div>
              <p className="achievement--text">{achievement.description}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
