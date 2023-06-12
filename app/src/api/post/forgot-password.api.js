import { axiosInstance } from "../_base/axios-instance";

export async function forgotPassword({ email }) {
  const response = await axiosInstance.post(`/forgot-password`, {
    email,
  });
  return response.data;
}
