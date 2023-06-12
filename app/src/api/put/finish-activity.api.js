import { axiosInstance } from "../_base/axios-instance";

export async function finishActivity(activityId) {
  return await axiosInstance.put(`/activity/${activityId}/finish-activity`, {});
}
