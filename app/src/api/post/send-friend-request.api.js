import { axiosInstance } from "../_base/axios-instance";

export async function sendFriendRequest(usuarioId) {
  return await axiosInstance.post(`/friendship/request/${usuarioId}`);
}
