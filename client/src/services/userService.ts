import { ErrorResponse } from "@/types/error";
import { cookies } from "next/headers";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const userService = {
  async getMe(): Promise<string> {
    const cookieStore = await cookies();
    const token = cookieStore.get("authToken")?.value;

    const response = await fetch(`${API_URL}/user/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      const error: ErrorResponse = await response.json();
      throw new Error(error.message);
    }

    const data = await response.text();
    return data;
  },
};
