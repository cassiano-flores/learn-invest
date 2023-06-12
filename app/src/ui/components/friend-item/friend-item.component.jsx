import React from "react";
import "./friend-item.style.css";
import { Button } from "../button/button.component";
import {
  acceptFriendRequest,
  deleteFriendship,
  sendFriendRequest,
  deleteFriendRequest,
} from "../../../api";

import accept from "../../../assets/buttons/accept.svg";
import cancel from "../../../assets/buttons/cancel.svg";
import exclude from "../../../assets/buttons/garbage.svg";

export function FriendItem({ menuName, data, setRefresh, myRequests }) {
  const { id, name, email, nickname, currentIcon } = data;

  async function handleAcceptFriendship(friendId) {
    await acceptFriendRequest(friendId);
    setRefresh(true);
  }

  async function handleDenyFriendship(id) {
    await deleteFriendRequest(id);
    setRefresh(true);
  }

  async function handleDeleteFriendship(friendId) {
    await deleteFriendship(friendId);
    setRefresh(true);
  }

  async function handleSendFriendRequest(friendId) {
    setRefresh(false);
    await sendFriendRequest(friendId);
    setRefresh(true);
  }

  function renderActionMenu(menuName) {
    switch (menuName) {
      case "friend-requests":
        return (
          <>
            <img
              onClick={() => handleAcceptFriendship(id)}
              className="friend-item--button-image"
              src={accept}
              alt="Círculo verde com um sinal representando aceitação, botão usado para aceitar requisição de amizade"
            />

            <img
              onClick={() => handleDenyFriendship(id)}
              className="friend-item--button-image"
              src={cancel}
              alt="Círculo vermelho com um sinal representando rejeição, botão usado para negar requisição de amizade"
            />
          </>
        );

      case "friend-list":
        return (
          <Button
            onClick={() => handleDeleteFriendship(id)}
            className="friend-item--delete-button"
          >
            <img
              className="friend-item--delete-button-icon"
              src={exclude}
              alt="excluir"
            />
          </Button>
        );

      case "friend-request":
        if (myRequests.map((request) => request.id).includes(id)) {
          return <p className="list-rankers--message">Enviado</p>;
        }

        return (
          <Button
            className="friend-item--add-button"
            onClick={() => handleSendFriendRequest(id)}
          >
            Adicionar
          </Button>
        );

      default:
        break;
    }
  }

  return (
    <>
      <div className="friend-item--container">
        <div className="friend-item--information-container">
          <div className="friend-item--image-profile-container">
            <img
              className="friend-item--image-profile"
              src={`../icons/${currentIcon.id}.svg`}
              alt={`Ícone ${currentIcon.name}`}
            />
          </div>
          <div className="friend-item--information">
            <p className="friend-item--information-nickname">
              {nickname ? nickname : name}
            </p>
            <p className="friend-item--information-email">{email}</p>
          </div>
        </div>
        <div className="friend-item--actions">{renderActionMenu(menuName)}</div>
      </div>
    </>
  );
}
