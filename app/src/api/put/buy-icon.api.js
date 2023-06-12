import { axiosInstance } from "../_base/axios-instance";

export async function buyIcon(iconId) {
  return await axiosInstance.put(`/store/icons/${iconId}`);
}
