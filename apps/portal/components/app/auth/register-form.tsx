

import {useRegisterForm} from "@/lib/hooks/use-register-form";

export default function RegisterForm() {
    
    const form = useRegisterForm();


    const handleSubmit: React.SubmitEventHandler<HTMLFormElement> = (e) => {
        e.preventDefault();
        form.handleSubmit((data) => {
     
        })(e);
    }

    return (
        <>

            <form onSubmit={handleSubmit} className="space-y-4">
                
            </form>
        </>
    )


}
