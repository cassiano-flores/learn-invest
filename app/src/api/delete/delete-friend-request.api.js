import { axiosInstance } from "../_base/axios-instance";

export async function deleteFriendRequest(usuarioId) {
  return await axiosInstance.delete(`/friendship/request/${usuarioId}`);
}
