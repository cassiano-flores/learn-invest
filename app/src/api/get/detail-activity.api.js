import { axiosInstance } from "../_base/axios-instance";

export async function detailActivity(activityId) {
  const response = await axiosInstance.get(`/activity/${activityId}`);

  return response.data;
}
