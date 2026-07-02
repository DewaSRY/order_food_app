import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

import type { LoginForm } from "@/lib/schema/auth-schema";
import { LoginFormSchema } from "@/lib/schema/auth-schema";

export function useLoginForm() {
  return useForm<LoginForm>({
    resolver: zodResolver(LoginFormSchema),
  });
}
