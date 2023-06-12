import { axiosInstance } from "../_base/axios-instance";

export async function editProfile(form) {
  const response = await axiosInstance.put(`/profile/edit`, {
    name: form.name,
    nickname: form.nickname,
    iconId: Number(form.iconId),
  });

  return response.data;
}
