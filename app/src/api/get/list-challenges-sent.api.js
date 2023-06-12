import { axiosInstance } from "../_base/axios-instance";

export async function listChallengesSent() {
  const response = await axiosInstance.get(`/league/challenges/sent`);

  return response.data;
}
