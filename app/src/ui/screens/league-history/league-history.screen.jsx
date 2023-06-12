import {
  NavBar,
  ScreenContainer,
  Loader,
  LeagueHistoryCard,
  Button,
} from "../../components";
import { useEffect, useState } from "react";
import { listChallengeHistory } from "../../../api";

import { usePagination, useValidateAutentication } from "../../../hooks";

import "./league-history.style.css";

export function LeagueHistory() {
  const PAGE_SIZE = 10;
  const INITIAL_PAGE = 0;
  const ONE = 1;
  const [isLoading, setIsLoading] = useState(true);
  const [, setError] = useState();
  const [history, setHistory] = useState([]);
  const { validateAutentication } = useValidateAutentication();
  const { page, goToNextPage, goToPreviousPage } = usePagination();

  async function fetchHistory() {
    try {
      setIsLoading(true);
      const history = await listChallengeHistory(PAGE_SIZE, page);
      setHistory(history);
    } catch (error) {
      validateAutentication(error.response.status);
      setError("Não conseguimos abrir o histórico");
    } finally {
      setIsLoading(false);
    }
  }

  useEffect(() => {
    fetchHistory();
  }, [page]);

  function showHistoryOrNothing() {
    if (history.content.length === 0) {
      return <p className="league-history-title">Você ainda não tem desafios</p>
    } else {
      return <div className="container">
          <p className="league-history-title">HISTÓRICO</p>
          <div className="league-history-division">
            <p>Desafiante</p>
            <p>Desafiado</p>
          </div>

          <div className="league-history-all-cards">
            {
              history.content.map((card, index) => <LeagueHistoryCard key={index} card={card} />)
            }
          </div>

          <div className="league-history-buttons">
            {page === INITIAL_PAGE ? null : <Button onClick={goToPreviousPage}> Voltar </Button>}
            {page === (history.totalPages - ONE) ? null : <Button onClick={goToNextPage}> Avançar
              </Button>}
          </div>
        </div>
    }
  }

  return (
    <ScreenContainer>
      {isLoading ?
        <div className="centralize-loader">
          <Loader />
        </div>
       :
        showHistoryOrNothing()
      }

      <NavBar />
    </ScreenContainer>
  );
}
