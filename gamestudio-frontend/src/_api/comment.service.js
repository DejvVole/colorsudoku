import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchComments = game => gsAxios.get('/comment/' + game);

export const addComment = (game, player, comment_text) => gsAxios.post('/comment/', {
    player, comment_text, game, writtenAt: formatDate(new Date()),
})
