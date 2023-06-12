import {
  ListRankers,
  NavBar,
  ScreenContainer,
  Loader,
  Button,
} from "../../components";
import { useEffect, useState } from "react";
import {
  listLeagueRank,
  listChallengesReceived,
  listChallengesSent,
} from "../../../api";

import { useSeeMore, useValidateAutentication } from "../../../hooks";
import { Link } from "react-router-dom";

import "./league.style.css";

export function League() {
  const PAGE_NUMBER = 0;
  const PLUS_ONE = 1;
  const [isLoading, setIsLoading] = useState(true);
  const [rankers, setRankers] = useState([]);
  const [receivedChallenges, setReceivedChallenges] = useState([]);
  const [sentChallenges, setSentChallenges] = useState([]);
  const { validateAutentication } = useValidateAutentication();
  const { pageSize, seeMore } = useSeeMore();

  async function fetchRankers() {
    try {
      setIsLoading(true);
      const rankers = await listLeagueRank(pageSize, PAGE_NUMBER);
      const received = await listChallengesReceived();
      const sent = await listChallengesSent();
      setReceivedChallenges(received);
      setSentChallenges(sent);
      setRankers(rankers);
    } catch (error) {
      validateAutentication(error.response.status);
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {
    fetchRankers();
  }, [pageSize]);

  function showButton() {
    if(rankers.size <= rankers.totalElements) {
      return <Button onClick={seeMore}>Veja mais</Button>
    }
  }

  return (
    <ScreenContainer>
      <div className="container">
        {isLoading ? (
          <div className="centralize-loader">
            <Loader />
          </div>
        ) : (
          <>
            <div className="league--title-container">
              <p className="league--title">Liga</p>
              <Link to="/league/history" className="league--history-button">
                Histórico
              </Link>

              <Link to="/league/help" className="league--help-button">
                ?
              </Link>
            </div>
            <div className="list-rankers--container">
              {rankers.content.map((ranker, index) => (
                <ListRankers
                  key={ranker.id}
                  posicao={index + PLUS_ONE}
                  ranker={ranker}
                  haveSent={sentChallenges.filter(
                    (sent) => sent.usuarioReceiverId === ranker.id
                  )}
                  haveReceived={receivedChallenges.filter(
                    (received) => received.usuarioSenderId === ranker.id
                  )}
                />
              ))}
            </div>
            {showButton()}
          </>
        )}
      </div>

      <NavBar />
    </ScreenContainer>
  );
}
