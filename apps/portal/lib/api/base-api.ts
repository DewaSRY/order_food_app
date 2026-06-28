import axios from "axios";

export class BaseAPI {
  protected axiosInstance: any;

  constructor() {
    this.setupAxiosInstance();
  }
  private setupAxiosInstance() {
    this.axiosInstance = axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_URL,
      headers: {
        "Content-Type": "application/json",
      },
    });
  }

  private setupRequestInterceptors() {}
  private setupResponseInterceptors() {}

  private setupAuthrizationHeader() {}
}
