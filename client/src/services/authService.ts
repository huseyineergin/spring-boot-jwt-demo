import { AuthRequest, AuthResponse } from "@/types/auth";
import { ErrorResponse } from "@/types/error";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const authService = {
  async signIn(request: AuthRequest): Promise<AuthResponse> {
    const response = await fetch(`${API_URL}/auth/signIn`, {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      const error: ErrorResponse = await response.json();
      throw new Error(error.message);
    }

    const data: AuthResponse = await response.json();
    return data;
  },

  async signUp(request: AuthRequest): Promise<AuthResponse> {
    const response = await fetch(`${API_URL}/auth/signUp`, {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      const error: ErrorResponse = await response.json();
      throw new Error(error.message);
    }

    const data: AuthResponse = await response.json();
    return data;
  },
};
