import React, { useEffect, useState } from "react";

import "./find-friend.style.css";
import {
  Input,
  NavBar,
  Button,
  FriendItem,
  ScreenContainer,
} from "../../components";
import search from "../../../assets/buttons/search.svg";
import { useForm } from "../../../hooks";
import { listFriends, listMyFriendRequests, searchUsers } from "../../../api";
import useGlobalUser from "../../../context/usuario/usuario.context";
import { useValidateAutentication } from "../../../hooks";
export function FindFriend() {
  const [user] = useGlobalUser();
  const [friends, setFriends] = useState([]);
  const { formInput, setData } = useForm();
  const [users, setUsers] = useState([]);
  const [myRequests, setMyRequests] = useState([]);
  const { validateAutentication } = useValidateAutentication();
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    async function fetchFriends() {
      try {
        const friends = await listFriends();
        setFriends(friends);
      } catch (error) {
        validateAutentication(error.response.status);
      }
    }

    fetchFriends();
  }, []);

  useEffect(() => {
    async function fetchMyRequests() {
      try {
        const requests = await listMyFriendRequests();
        setMyRequests(requests);
      } catch (error) {
        validateAutentication(error.response.status);
      }
    }
    fetchMyRequests();
  }, [refresh]);

  useEffect(() => {
    handleSearchFriend();
  }, [formInput]);

  function handleChange(event) {
    setData(event.target);
  }

  async function handleSearchFriend() {
    const users = await searchUsers(formInput?.text, 10, 0);
    setUsers(users.content);
  }

  return (
    <ScreenContainer>
      <div className="container">
        <div className="find-friend--input-container input-container">
          <Input
            label="USUÁRIOS"
            name="text"
            onChange={handleChange}
            placeholder="Pesquisar"
          />
          <div className="find-friend--search-button">
            <img
              className="find-friend--search-button-icon"
              src={search}
              alt="search"
            />
          </div>
        </div>
        {users.map((element, index) => {
          return element.id !== user.id &&
            !friends.content.map((friend) => friend.id).includes(element.id) ? (
            <FriendItem
              key={index}
              menuName="friend-request"
              data={element}
              setRefresh={setRefresh}
              myRequests={myRequests}
            />
          ) : null;
        })}
      </div>

      <NavBar />
    </ScreenContainer>
  );
}
