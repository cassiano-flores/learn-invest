import { axiosInstance } from "../_base/axios-instance";

export async function listCoursesAvailable() {
  const response = await axiosInstance.get(`/course/available`);

  return response.data;
}
