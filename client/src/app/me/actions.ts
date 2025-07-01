import { userService } from "@/services/userService";

export async function getMeAction() {
  try {
    const username = await userService.getMe();
    return username;
  } catch (error) {
    if (error instanceof Error) {
      return {
        message: error.message,
      };
    }
  }
}
