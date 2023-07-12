import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchScore = game => gsAxios.get('/score/' + game);

export const addScore = (game, player, points) => gsAxios.post('/score', {
    player, game, points, playedAt: formatDate(new Date()),
})
