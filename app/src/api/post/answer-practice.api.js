import { axiosInstance } from "../_base/axios-instance";

export async function answerPractice(questionId, answer) {
  const response = await axiosInstance.post(`/question/${questionId}`, {
    answer,
  });

  return response.data.correct;
}
