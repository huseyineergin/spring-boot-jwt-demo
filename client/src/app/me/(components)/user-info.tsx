"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";
import { signOutAction } from "../actions";

export default function UserInfo({ result }: { result: string }) {
  const [error, setError] = useState<{ message: string }>({ message: "" });
  const router = useRouter();

  async function handleSignOut() {
    const result = await signOutAction();
    if (result) return setError(result);
    router.push("/auth");
  }

  return (
    <div className="flex flex-col justify-center items-center min-h-screen ">
      <div className="w-full max-w-md p-4 border">
        <h1 className="text-2xl text-center font-bold mb-4">Me</h1>
        <div className="text-center">Welcome {result}!</div>
        <button onClick={handleSignOut} className="w-full p-1 mt-2 border">
          Sign Out
        </button>
        {!error.message && <p className="text-red-500 text-center">{error.message}</p>}
      </div>
    </div>
  );
}
