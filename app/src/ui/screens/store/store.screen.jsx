import { useEffect, useState } from "react";
import { buyIcon, listIcons, listUserIcons, userStatus } from "../../../api";
import useGlobalUser from "../../../context/usuario/usuario.context";
import { Button, Loader, NavBar, ScreenContainer } from "../../components";
import "./store.style.css";
import { useValidateAutentication } from "../../../hooks";
import coins from "../../../assets/coin/coins.svg";

export function Store() {
  const PAGE_SIZE = 15;
  const PAGE_NUMBER = 0;
  const [user, setUser] = useGlobalUser();
  const [isLoading, setLoading] = useState(true);

  const [icons, setIcons] = useState([]);
  const [userIcons, setUserIcons] = useState([]);
  const { validateAutentication } = useValidateAutentication();
  const [userCoins, setUserCoins] = useState();

  const [refreshIconsStatus, setRefreshIconsStatus] = useState(false);

  async function fetchIcons() {
    try {
      setLoading(true);
      const icons = await listIcons(PAGE_SIZE, PAGE_NUMBER);
      const userIcons = await listUserIcons();
      setIcons(icons.content);
      setUserIcons(userIcons);
    } catch (error) {
      validateAutentication(error.response.status);
    } finally {
      setLoading(false);
    }
  }

  async function buyOrEquip(icon) {
    try {
      await buyIcon(icon.id);
      setUser({
        ...user,
        currentIcon: {
          id: icon.id,
          name: icon.name,
          price: icon.price,
          borderImage: icon.borderImage,
        },
      });
      const userIcons = await listUserIcons();
      setUserIcons(userIcons);
      setRefreshIconsStatus(true);
    } catch (error) {
      validateAutentication(error.response.status);
    }
  }

  useEffect(() => {
    fetchUserCoins();
    fetchIcons();
  }, []);

  async function fetchUserCoins() {
    try {
      const { coins } = await userStatus();
      setUserCoins(coins);
    } catch (error) {
      validateAutentication(error.response.status);
    }
  }

  function handleBuyIcon(icon) {
    buyOrEquip(icon);
    fetchUserCoins();
  }

  useEffect(() => {
    renderIconList();
    fetchUserCoins();
    setRefreshIconsStatus(false);
  }, [refreshIconsStatus]);

  function renderIconStatus(icon) {
    if (
      icon.price > userCoins &&
      !userIcons.map((userIcon) => userIcon.id).includes(icon.id)
    ) {
      return (
        <div className="store-button store-button-disable">
          <div className="store--price-container">
            {icon.price}
            <img className="store--moedas-icon" src={coins} alt="moedas" />
          </div>
        </div>
      );
    }

    if (!userIcons.map((userIcon) => userIcon.id).includes(icon.id)) {
      return (
        <Button className="store-button store-button-enable" onClick={() => handleBuyIcon(icon)}>
          <div className="store--price-container">
            {icon.price}
            <img className="store--moedas-icon" src={coins} alt="moedas" />
          </div>
        </Button>
      );
    }
    if (user.currentIcon.id === icon.id) {
      return <div className="store-button store-button-disable">Equipado</div>;
    } else {
      return (
        <Button className="store-button store-button-enable" onClick={() => buyOrEquip(icon)}>
          Equipar
        </Button>
      );
    }
  }

  function renderIconList() {
    return icons.map((icon) => (
      <div className="store--icon-button-container" key={icon.id}>
        <div className="store--icon-name-container">
          <img
            className="store--icon"
            src={`../../icons/${icon.id}.svg`}
            alt={`Ícone de ${icon.name}`}
          />
          <p className="store--icon-name">{icon.name}</p>
        </div>
        {renderIconStatus(icon)}
      </div>
    ));
  }

  return (
    <ScreenContainer>
      <div className="container store--container">
        <div className="store--icons-container">
          {isLoading ? (
            <div className="centralize-loader">
              <Loader />
            </div>
          ) : (
            <>
              <p className="store--title">Icones Disponíveis</p>
              <div className="store--icons-container">{renderIconList()}</div>
            </>
          )}
        </div>
      </div>

      <NavBar />
    </ScreenContainer>
  );
}
