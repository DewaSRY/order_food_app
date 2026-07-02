import { z } from "zod";

export const RegisterFormSchema = z
  .object({
    email: z.email({ message: "Invalid email address" }),
    password: z
      .string()
      .min(6, { message: "Password must be at least 6 characters long" }),
    confirmPassword: z.string().min(6, {
      message: "Confirm Password must be at least 6 characters long",
    }),
    username: z.string().min(3, { message: "Username must be at least 3 characters long" }),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

export type RegisterForm = z.infer<typeof RegisterFormSchema>;

export const LoginFormSchema = z.object({
  email: z.email({ message: "Invalid email address" }),
  password: z.string().min(6, { message: "Password must be at least 6 characters long" }),
});


export type LoginForm = z.infer<typeof LoginFormSchema>;