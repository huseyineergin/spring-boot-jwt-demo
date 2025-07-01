"use server";

import { authService } from "@/services/authService";
import { AuthResponse } from "@/types/auth";
import { cookies } from "next/headers";

export async function signInAction(formData: FormData) {
  const cookieStore = await cookies();

  try {
    const username = formData.get("username") as string;
    const password = formData.get("password") as string;

    const data: AuthResponse = await authService.signIn({
      username,
      password,
    });

    cookieStore.set({
      name: "authToken",
      value: data.token,
      maxAge: 10 * 60,
      httpOnly: true,
      path: "/",
    });
  } catch (error) {
    if (error instanceof Error) {
      return {
        message: error.message,
      };
    }
  }
}

export async function signUpAction(formData: FormData) {
  const cookieStore = await cookies();

  try {
    const username = formData.get("username") as string;
    const password = formData.get("password") as string;

    const data: AuthResponse = await authService.signUp({
      username,
      password,
    });

    cookieStore.set({
      name: "authToken",
      value: data.token,
      maxAge: 10 * 60,
      httpOnly: true,
      path: "/",
    });
  } catch (error) {
    if (error instanceof Error) {
      return {
        message: error.message,
      };
    }
  }
}
