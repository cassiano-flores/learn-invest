import { axiosInstance } from "../_base/axios-instance";

export async function userStatus() {
  const response = await axiosInstance.get(`/profile/status`);

  return response.data;
}
