import createGlobalState from "react-create-global-state";

const USER_KEY = "user";
const INITIAL_DATA = null;

const stateInStorage = localStorage.getItem(USER_KEY);

const initialState = stateInStorage ? JSON.parse(stateInStorage) : INITIAL_DATA;

const [_useGlobalUser, Provider] = createGlobalState(initialState);

const useGlobalUser = () => {
  const [_user, _setUser] = _useGlobalUser();

  const setUser = (user) => {
    _setUser(user);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  };

  return [_user, setUser];
};

export const GlobalUserProvider = Provider;

export default useGlobalUser;
