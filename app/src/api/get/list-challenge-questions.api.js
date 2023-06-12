import { axiosInstance } from "../_base/axios-instance";

export async function listChallengeQuestions() {
  const response = await axiosInstance.get(`/league/questions`);

  return response.data;
}
