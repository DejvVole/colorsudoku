import "../css/Comments.css"
import React from "react";
function Comments({comments}){
    return(
        <div className="comments-container">
            <h1>COMMENTS</h1>
            <div className="table">
                <table className="comments-table">
                    <thead>
                    <tr>
                        <th>PLAYER</th>
                        <th>COMMENT</th>
                        <th>DATE</th>
                    </tr>
                    </thead>

                    <tbody>
                    {comments.map(comment => (
                        <tr key={`comment-${comment.ident}`}>
                            <th>{comment.player}</th>
                            <th>{comment.comment_text}</th>
                            <th>{new Date(comment.writtenAt).toLocaleDateString()}</th>
                        </tr>
                    ))}

                    </tbody>
                </table>
            </div>

        </div>

    );
}

export default Comments;