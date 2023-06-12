import { axiosInstance } from "../_base/axios-instance";

export async function listMyFriendRequests() {
  const response = await axiosInstance.get(`/profile/my_friend_requests`);

  return response.data;
}
