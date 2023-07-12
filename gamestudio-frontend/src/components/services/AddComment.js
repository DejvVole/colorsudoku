import { useForm } from "react-hook-form";
import React, {useState} from "react";

function AddComment({onSendComment}){
    const { register, handleSubmit, formState: { errors } } = useForm();
    const[commentSend, setCommentSend] = useState(false);

    const onSubmit = (data, e) => {
        onSendComment(data.comment);
        setCommentSend(!commentSend);
        e.target.reset();
    };

    return(
        <form onSubmit={handleSubmit(onSubmit)} className="from">

            <h3>COMMENT TEXT</h3>
            <input className="username-input"
                   type="text"
                   {...register("comment", {
                       minLength: {value: 3, message: "MIN LENGTH IS 3"},
                       maxLength: {value: 20, message: "MAX LENGTH IS 20"},
                       required: {value: true,}
                   })}
                   placeholder="ENTER YOUR COMMENT"/>

            <br/>
            <input className="submit-button" type="submit" value="SEND"/>

            <div className="errors">
                {errors.comment?.type === 'required' && <div className="error">COMMENT IS REQUIRED</div>}
                {errors.comment?.type === 'maxLength' && <div className="error">MAX LENGTH IS 20</div>}
                {errors.comment?.type === 'minLength' && <div className="error">MIN LENGTH IS 3</div>}
            </div>

            <div>
                {commentSend && <div className="success">COMMENT SAVED</div>}
            </div>
        </form>

    )
}

export default AddComment;