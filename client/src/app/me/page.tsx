import { redirect } from "next/navigation";
import { getMeAction } from "./actions";

export default async function Me() {
  const result = await getMeAction();

  if (typeof result !== "string") redirect("/auth");

  return (
    <div className="flex flex-col justify-center items-center min-h-screen ">
      <div className="w-full max-w-md p-4 border">
        <h1 className="text-2xl text-center font-bold mb-4">Me</h1>
        <div className="text-center">Welcome {result}!</div>
      </div>
    </div>
  );
}
