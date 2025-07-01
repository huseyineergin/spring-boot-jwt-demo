import { redirect } from "next/navigation";
import UserInfo from "./(components)/user-info";
import { getMeAction } from "./actions";

export default async function Me() {
  const result = await getMeAction();

  if (typeof result !== "string") redirect("/auth");

  return <UserInfo result={result} />;
}
