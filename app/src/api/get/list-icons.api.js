import { axiosInstance } from "../_base/axios-instance";

export async function listIcons(pageSize, pageNumber) {
  const response = await axiosInstance.get(
    `/store/icons?size=${pageSize}&page=${pageNumber}`
  );

  return response.data;
}
