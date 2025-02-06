import axios from "axios";
import { meta } from "@/config/config";

const api = axios.create({
  baseURL: meta.baseUrl,
  timeout: 3000,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.response.use(
  (response) => {
    return response.data;
  }
);

export default api;
