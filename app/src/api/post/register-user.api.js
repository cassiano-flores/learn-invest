import { axiosInstance } from '../_base/axios-instance'

export async function registerUser(form) {
  const response = await axiosInstance.post(`/register`, {
    name: form.name,
    nickname: form.nickname,
    email: form.email,
    password: form.password,
  });

  return response.data
}
