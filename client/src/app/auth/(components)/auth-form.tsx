"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import { signInAction, signUpAction } from "../actions";

export default function AuthForm() {
  const router = useRouter();
  const [error, setError] = useState({ message: "" });

  async function handleSignIn(formData: FormData) {
    const result = await signInAction(formData);
    if (result) return setError(result);
    router.push("/me");
  }

  async function handleSignUp(formData: FormData) {
    const result = await signUpAction(formData);
    if (result) return setError(result);
    router.push("/me");
  }

  return (
    <form className="flex flex-col gap-2 p-4 border">
      {error && <div className="text-red-500 text-center">{error.message}</div>}

      <div className="flex flex-col">
        <label htmlFor="username">Username</label>
        <input id="username" name="username" type="text" className="border p-1" required />
      </div>

      <div className="flex flex-col">
        <label htmlFor="password">Password</label>
        <input id="password" name="password" type="password" className="border p-1" required />
      </div>

      <div className="flex gap-x-2">
        <button formAction={handleSignIn} type="submit" className="flex-1 p-1 border">
          Sign In
        </button>

        <button formAction={handleSignUp} type="submit" className="flex-1 p-1 border">
          Sign Up
        </button>
      </div>
    </form>
  );
}
