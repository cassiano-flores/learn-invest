import { axiosInstance } from "../_base/axios-instance";

export async function replyChallenge(usuarioId, score) {
  const response = await axiosInstance.put(`/league/challenge/${usuarioId}`, {
    score,
  });

  return response.data;
}
