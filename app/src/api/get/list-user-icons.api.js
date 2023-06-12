import { axiosInstance } from "../_base/axios-instance";

export async function listUserIcons() {
  const response = await axiosInstance.get(`/profile/icons`);

  return response.data;
}
