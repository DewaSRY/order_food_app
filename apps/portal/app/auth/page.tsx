"use client";

import { useState } from "react";
import { LoginForm } from "@/components/form/login-form";
import { RegisterForm } from "@/components/form/register-form";

export default function AuthPage() {
  const [activeTab, setActiveTab] = useState<"login" | "register">("login");

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800 py-12 px-4">
      <div className="w-full max-w-md">
        {/* Header */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
            Welcome
          </h1>
          <p className="text-gray-600 dark:text-gray-300">
            {activeTab === "login"
              ? "Sign in to your account"
              : "Create a new account"}
          </p>
        </div>

        {/* Card */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-8">
          {/* Tabs */}
          <div className="flex gap-2 mb-8 bg-gray-100 dark:bg-gray-700 p-1 rounded-lg">
            <button
              onClick={() => setActiveTab("login")}
              className={`flex-1 py-2 px-4 rounded-md font-medium transition-all ${
                activeTab === "login"
                  ? "bg-white dark:bg-gray-600 text-gray-900 dark:text-white shadow-sm"
                  : "text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
              }`}
            >
              Sign In
            </button>
            <button
              onClick={() => setActiveTab("register")}
              className={`flex-1 py-2 px-4 rounded-md font-medium transition-all ${
                activeTab === "register"
                  ? "bg-white dark:bg-gray-600 text-gray-900 dark:text-white shadow-sm"
                  : "text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
              }`}
            >
              Sign Up
            </button>
          </div>

          {/* Form Content */}
          <div>
            {activeTab === "login" && (
              <div>
                <LoginForm />
                <p className="mt-6 text-center text-sm text-gray-600 dark:text-gray-400">
                  Don&apos;t have an account?{" "}
                  <button
                    onClick={() => setActiveTab("register")}
                    className="text-blue-600 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300 font-medium"
                  >
                    Sign up
                  </button>
                </p>
              </div>
            )}

            {activeTab === "register" && (
              <div>
                <RegisterForm />
                <p className="mt-6 text-center text-sm text-gray-600 dark:text-gray-400">
                  Already have an account?{" "}
                  <button
                    onClick={() => setActiveTab("login")}
                    className="text-blue-600 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300 font-medium"
                  >
                    Sign in
                  </button>
                </p>
              </div>
            )}
          </div>
        </div>

        {/* Footer */}
        <div className="mt-8 text-center text-sm text-gray-600 dark:text-gray-400">
          <p>
            By continuing, you agree to our{" "}
            <a href="#" className="text-blue-600 hover:text-blue-700 dark:text-blue-400">
              Terms of Service
            </a>{" "}
            and{" "}
            <a href="#" className="text-blue-600 hover:text-blue-700 dark:text-blue-400">
              Privacy Policy
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}
