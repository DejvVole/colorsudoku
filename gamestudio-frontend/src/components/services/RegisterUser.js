import "../css/Register.css"

import {Button, Form} from "react-bootstrap";
import { useForm } from "react-hook-form";
import bcrypt from 'bcryptjs';
import React, {useEffect, useState} from "react";
import {fetchUser} from "../../_api/registeruser.service";


function RegisterUser({onRegister}){
    const {
        register, handleSubmit, formState: { errors}
    } = useForm();

    const [getErr, setErr] = useState(false);
    const[registerSucc, setRegisterSucc] = useState(false);

    const onSubmit = (data, e) => {
        fetchUser(data.username).then(response => {
            if(response.data === ''){
                const hashedPasswd = bcrypt.hashSync(data.password, 5);
                onRegister(data, hashedPasswd);
                setRegisterSucc(!registerSucc);
                setErr(false);
            }else{
                setErr(true);
            }
        });
        e.target.reset();
    };

    return(
        <form onSubmit={handleSubmit(onSubmit)} className="from">
            <h1>REGISTER</h1>
            <h3>USERNAME</h3>
            <input type="text" className="username-input"
                   {...register("username",
                       { required: true,
                           maxLength: {value: 150, message: "MAX LENGTH IS 150"}})}
                   aria-invalid={errors.username ? "true" : "false"}
                   placeholder="ENTER YOUR USERNAME"/>


            <h3>PASSWORD</h3>
            <input type="password" className="register-password password-input"
                   {...register("password",
                       { required: true,
                           pattern: {value: /^(?=.*\d)(?=.*[A-Z])(?=.*\W).{8,}$/, message:"WEAK PASSWORD"},
                           minLength: {value: 5, message: "MIN LENGTH IS 5"}})}
                   aria-invalid={errors.password ? "true" : "false"}
                   placeholder="ENTER YOUR PASSWORD"
            />


            <p className="password-characters">
                THE PASSWORD SHOULD CONTAIN AT LEAST ONE LOWERCASE LETTER, ONE UPPERCASE LETTER, ONE NUMBER, AND ONE SPECIAL CHARACTER.
            </p>

            <input type="submit" className="submit-button" value="REGISTER"/>

            <div className="errors">
                {errors.username?.type === 'required' && <div className="error username-error">FIRST NAME IS REQUIRED</div>}
                {errors.username?.type === 'maxLength' && <div className="error username-error">MAX LENGTH IS 150</div>}

                {errors.password?.type === 'required' && <div className="error password-error">PASSWORD IS REQUIRED</div>}
                {errors.password?.type === 'pattern' && <div className="error password-error">WEAK PASSWORD</div>}
                {errors.password?.type === 'minLength' && <div className="error password-error">MIN LENGTH IS 5</div>}

                {getErr &&
                    <p className="error">THIS USERNAME IS ALREADY TAKEN</p>
                }
            </div>

            <div>
                {registerSucc && <div className="success">REGISTRATION SUCCESSFUL</div>}
            </div>
        </form>

    )
}

export default RegisterUser;