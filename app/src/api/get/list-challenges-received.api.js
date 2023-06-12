import { axiosInstance } from "../_base/axios-instance";

export async function listChallengesReceived() {
  const response = await axiosInstance.get(`/league/challenges/received`);

  return response.data;
}
