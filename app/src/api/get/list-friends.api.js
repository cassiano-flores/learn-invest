import { axiosInstance } from "../_base/axios-instance";

export async function listFriends(pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/profile/friends?size=${pageSize}&page=${pageNumber}`
  );

  return response.data;
}
