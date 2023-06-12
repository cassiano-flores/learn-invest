import { axiosInstance } from "../_base/axios-instance";

export async function resetPassword(request, token) {
  const response = await axiosInstance.put(
    `/reset-password?token=${token}`,
    request
  );

  return response.data;
}
