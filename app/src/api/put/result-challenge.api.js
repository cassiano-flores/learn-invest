import { axiosInstance } from "../_base/axios-instance";

export async function resultChallenge({ challengeId }) {
  return await axiosInstance.put(`/league/challenge/${challengeId}/result`);
}
