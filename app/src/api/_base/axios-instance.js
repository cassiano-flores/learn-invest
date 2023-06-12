import axios from "axios";
import { API_URL } from "../../constants/index";

export const axiosInstance = axios.create({
  baseURL: API_URL,
  timeout: 9000,
  withCredentials: true,
});
