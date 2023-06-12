import { axiosInstance } from "../_base/axios-instance";

export async function listActivities() {
  const response = await axiosInstance.get(`/activity`);

  return response.data;
}
