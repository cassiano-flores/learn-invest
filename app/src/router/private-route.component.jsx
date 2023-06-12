import useGlobalUser from "../context/usuario/usuario.context";
import { Navigate } from "react-router-dom";

export function PrivateRoute({ Screen }) {
  const [user] = useGlobalUser();

  if (user) {
    return <Screen />;
  }

  return <Navigate to="/" />;
}
