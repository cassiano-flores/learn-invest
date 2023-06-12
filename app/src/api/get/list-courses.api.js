import { axiosInstance } from "../_base/axios-instance";

export async function listCourses() {
  const response = await axiosInstance.get(`/course`);

  return response.data;
}
