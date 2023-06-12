import { axiosInstance } from "../_base/axios-instance";

export async function detailProfile() {
  const response = await axiosInstance.get(`/profile`);

  return response.data;
}
