import { axiosInstance } from "../_base/axios-instance";

export async function acceptFriendRequest(usuarioId) {
  return await axiosInstance.post(`/friendship/accept/${usuarioId}`);
}
