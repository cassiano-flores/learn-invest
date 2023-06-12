import { axiosInstance } from "../_base/axios-instance";

export async function listChallengeHistory(pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/league/history?size=${pageSize}&page=${pageNumber}&sort=challengedIn,desc`
  );

  return response.data;
}
