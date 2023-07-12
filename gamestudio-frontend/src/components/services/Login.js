import "../css/Login.css"

import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import {addUser, fetchUser} from "../../_api/registeruser.service";
import React, {useState} from "react";
import bcrypt from "bcryptjs";

function Login({onLogin}){
    const {
        register, handleSubmit, formState: { errors}
    } = useForm();
    const navigate = useNavigate();

    const [userExist, setUserExist] = useState(false);
    const [passwdErr, setPasswdErr] = useState(false);


    const onSubmit = (data, e) => {
        setUserExist(false);
        setPasswdErr(false);
        fetchUser(data.username).then(response => {
            if(response.data === ''){
                setUserExist(true);
            }else {
                bcrypt.compare(data.password, response.data.password, function (err, isMatch){
                    if(err){
                        console.log("err");
                    } else if(!isMatch){
                        setPasswdErr(true);
                    } else {
                        onLogin(data);
                        navigate("/");
                    }
                });
            }
        });
        e.target.reset();
    };

    return(
        <form onSubmit={handleSubmit(onSubmit)} className="from">
            <h1>LOGIN</h1>
            <h3>USERNAME</h3>
            <input type="text" className="username-input"
                   {...register("username", { required: true })}
                   aria-invalid={errors.username ? "true" : "false"}
                   placeholder="ENTER YOUR USERNAME"
            />

            <h3>PASSWORD</h3>
            <input type="password" className="password-input"
                   {...register("password", { required: true })}
                   aria-invalid={errors.password ? "true" : "false"}
                   placeholder="ENTER YOUR PASSWORD"
            />
            <br/>

            <input type="submit" className="submit-button" value="LOGIN"/>

            <div className="errors">
                {errors.username?.type === 'required' && <div className="error username-error">FIRST NAME IS REQUIRED</div>}
                {errors.password?.type === 'required' && <div className="error password-error">PASSWORD IS REQUIRED</div>}

                {passwdErr &&
                    <div className="error password-error">INCORRECT PASSWORD</div>
                }

                {userExist &&
                    <div className="error username-error">THIS USER DOES NOT EXIST</div>
                }
            </div>

        </form>

    )
}

export default Login;