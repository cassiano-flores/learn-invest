import { axiosInstance } from "../_base/axios-instance";

export async function includeQuestion(form) {
  const response = await axiosInstance.post(`/question`, {
    activityId: form.activityId,
    questionText: form.questionText,
    alternativeA: form.alternativeA,
    alternativeB: form.alternativeB,
    alternativeC: form.alternativeC,
    alternativeD: form.alternativeD,
    answer: form.answer,
  });

  return response.data;
}
