import { axiosInstance } from "../_base/axios-instance";

export async function listPracticeQuestions(activityId) {
  const response = await axiosInstance.get(`/activity/${activityId}/practice`);

  return response.data;
}
