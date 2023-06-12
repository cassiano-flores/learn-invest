import { useEffect, useState } from "react";
import { userStatus } from "../../../api";
import useGlobalUser from "../../../context/usuario/usuario.context";
import { HeaderStats } from "../../components";
import "./screen-container.style.css";

export function ScreenContainer({ children }) {
  const [user] = useGlobalUser();
  const [status, setStatus] = useState();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function fetchStatus() {
      setIsLoading(true);
      const responseStatus = await userStatus();
      setStatus(responseStatus);
      setIsLoading(false);
    }

    fetchStatus();
  }, [user]);

  return (
    <div className="screen-container">
      <HeaderStats
        iconId={user.currentIcon.id}
        coins={isLoading ? "0" : status.coins}
        xp={isLoading ? "0" : status.xp}
        leagueTitle={isLoading ? "-" : status?.leagueTitle}
      />
      {children}
    </div>
  );
}
