import { axiosInstance } from "../_base/axios-instance";

export async function listActivitiesAvailable() {
  const response = await axiosInstance.get(`/activity/available`);

  return response.data;
}
