import { axiosInstance } from "../_base/axios-instance";

export async function detailCourse(courseId) {
  const response = await axiosInstance.get(`/course/${courseId}`);

  return response.data;
}
