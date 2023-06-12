import React, { useEffect, useState } from "react";
import "./profile.style.css";
import useGlobalUser from "../../../context/usuario/usuario.context";

import { useNavigate } from "react-router-dom";

import {
  FriendItem,
  NavBar,
  StatusDisplay,
  Button,
  Loader,
  Achievement,
} from "../../components";

import pencil from "../../../assets/buttons/pencil.svg";
import {
  detailProfile,
  userStatus,
  listFriendRequests,
  listFriends,
} from "../../../api";

import { useSeeMore, useValidateAutentication } from "../../../hooks";

export function Profile() {
  const PAGE_NUMBER = 0;
  const [user, setUser] = useGlobalUser();
  const [status, setStatus] = useState();
  const [friendRequests, setFriendRequests] = useState([]);
  const [friends, setFriends] = useState([]);
  const [refresh, setRefresh] = useState(false);

  const [isLoading, setLoading] = useState(true);
  const [isRequestsLoading, setRequestsLoading] = useState(true);
  const [isFriendsLoading, setFriendsLoading] = useState(true);

  const navigate = useNavigate();
  const { validateAutentication } = useValidateAutentication();
  const { pageSize, seeMore } = useSeeMore();

  async function fetchData() {
    try {
      setLoading(true);
      const detail = await detailProfile();
      setUser(detail);
      const status = await userStatus();
      setStatus(status);
    } catch (error) {
      validateAutentication(error.response.status);
    } finally {
      setLoading(false);
    }
  }

  async function fetchRequests() {
    try {
      setRequestsLoading(true);
      const request = await listFriendRequests();
      setFriendRequests(request);
    } catch (error) {
      validateAutentication(error.response.status);
    } finally {
      setRequestsLoading(false);
    }
  }

  async function fetchFriends() {
    try {
      setFriendsLoading(true);
      const friends = await listFriends(pageSize, PAGE_NUMBER);
      setFriends(friends);
    } catch (error) {
      validateAutentication(error.response.status);
    } finally {
      setFriendsLoading(false);
    }
  }

  useEffect(() => {
    fetchData();
    fetchRequests();
    fetchFriends();
  }, []);

  useEffect(() => {
    fetchRequests();
    setRefresh(false);
  }, [refresh]);

  useEffect(() => {
    fetchFriends();
    setRefresh(false);
  }, [refresh, pageSize]);

  function handleAddAmigo() {
    navigate("/profile/find");
  }

  function handleEditProfile() {
    navigate("/profile/edit");
  }

  return (
    <>
      {isLoading ? (
        <div className="profile-loader--container centralize-loader">
          <Loader />
        </div>
      ) : (
        <>
          <div className="profile--container">
            <div className="profile--container-background"></div>
            <div className="profile--profile-image-container">
              <img
                className="profile--profile-image"
                src={`./icons/${user.currentIcon.id}.svg`}
                alt={`ícone de ${user.name}`}
              />
              <img
                className="profile--edit-profile-image"
                src={pencil}
                alt="Ícone de um lápis que indica o botão de editar usuário"
                onClick={handleEditProfile}
              />
            </div>
            <div className="profile--data-container">
              <p className="profile--data-title">
                {user.nickname ? user.nickname : user.name}
              </p>
              <p className="profile--data">{user.email}</p>
              <p className="profile--data-member-container">
                {`MEMBRO DESDE ${user.createIn}`}
              </p>
              <StatusDisplay status="MOEDAS" valor={status.coins} />
              <StatusDisplay
                status={status.leagueTitle.toUpperCase()}
                valor={`${status.xp} XP`}
              />
            </div>
          </div>

          <div className="profile-achievement--container">
            <Achievement />
          </div>

          {isRequestsLoading ? (
            <div className="profile-loader--container">
              <Loader small={true} />
            </div>
          ) : (
            friendRequests[0] && (
              <div className="profile--friend-requests-container">
                <div className="profile--friends-title-container">
                  <p className="profile--friends-title">Pedidos de amizade</p>
                </div>
                {friendRequests.map((request, index) => (
                  <FriendItem
                    key={index}
                    menuName="friend-requests"
                    data={request}
                    setRefresh={setRefresh}
                  />
                ))}
              </div>
            )
          )}

          {isFriendsLoading ? (
            <div className="profile-loader--container">
              <Loader small={true} />
            </div>
          ) : (
            <div className="profile--friends-container">
              <div className="profile--friends-title-container">
                <p className="profile--friends-title">AMIGOS</p>
                <Button
                  onClick={handleAddAmigo}
                  className="profile--add-button"
                >
                  ADD AMIGOS
                </Button>
              </div>

              {!friends.content[0] && (
                <p className="profile--no-friends-message">
                  Você não possui amigos no momento.
                </p>
              )}
              {friends.content.map((friend, index) => (
                <FriendItem
                  key={index}
                  menuName="friend-list"
                  data={friend}
                  setRefresh={setRefresh}
                />
              ))}
              {friends.size < friends.totalElements ?? (
                <Button onClick={seeMore}>Veja mais</Button>
              )}
            </div>
          )}
        </>
      )}
      <NavBar />
    </>
  );
}
