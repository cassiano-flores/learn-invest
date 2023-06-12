import { axiosInstance } from "../_base/axios-instance";

export async function listActivityQuestions(activityId) {
  const response = await axiosInstance.get(`/activity/${activityId}/questions`);

  return response.data;
}
