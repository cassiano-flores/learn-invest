import { axiosInstance } from "../_base/axios-instance";

export async function searchUsers(text, pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/profile/search?text=${text}&size=${pageSize}&page=${pageNumber}`
  );

  return response.data;
}
