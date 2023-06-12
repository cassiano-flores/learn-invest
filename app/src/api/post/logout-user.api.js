import { axiosInstance } from "../_base/axios-instance";

export async function logoutUser() {
  await axiosInstance.post(`/logout`);
}
