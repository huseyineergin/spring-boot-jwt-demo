import AuthForm from "./(components)/auth-form";

export default function Auth() {
  return (
    <div className="flex flex-col justify-center items-center min-h-screen ">
      <div className="w-full max-w-md p-4 border">
        <h1 className="text-2xl text-center font-bold mb-4">Auth</h1>
        <AuthForm />
      </div>
    </div>
  );
}
