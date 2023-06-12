import { axiosInstance } from "../_base/axios-instance";

export async function listFriendRequests() {
  const response = await axiosInstance.get(`/profile/friend_requests`);

  return response.data;
}
