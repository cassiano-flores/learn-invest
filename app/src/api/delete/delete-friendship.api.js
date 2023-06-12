import { axiosInstance } from "../_base/axios-instance";

export async function deleteFriendship(usuarioId) {
  return await axiosInstance.delete(`/friendship/delete/${usuarioId}`);
}
