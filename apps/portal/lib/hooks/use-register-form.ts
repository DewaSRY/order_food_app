import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

import type { RegisterForm } from "@/lib/schema/auth-schema";
import { RegisterFormSchema } from "@/lib/schema/auth-schema";

export function useRegisterForm() {
  return useForm<RegisterForm>({
    resolver: zodResolver(RegisterFormSchema),
  });
}
