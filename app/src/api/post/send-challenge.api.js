import { axiosInstance } from "../_base/axios-instance";

export async function sendChallenge(usuarioId, score) {
  return await axiosInstance.post(`/league/challenge/${usuarioId}`, {
    score,
  });
}
