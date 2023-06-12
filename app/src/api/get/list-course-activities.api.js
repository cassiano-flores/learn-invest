import { axiosInstance } from "../_base/axios-instance";

export async function listCourseActivities(courseId) {
  const response = await axiosInstance.get(`/course/${courseId}/activities`);

  return response.data;
}
