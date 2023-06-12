import { axiosInstance } from "../_base/axios-instance";

export async function userAchievements(pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/profile/achievements?size=${pageSize}&page=${pageNumber}`
  );

  return response.data;
}
