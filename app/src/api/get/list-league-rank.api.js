import { axiosInstance } from "../_base/axios-instance";

export async function listLeagueRank(pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/league?size=${pageSize}&page=${pageNumber}`
  );

  return response.data;
}
