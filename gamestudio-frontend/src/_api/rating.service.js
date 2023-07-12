import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchAverageRating = game => gsAxios.get('/rating/' + game);

export const fetchRating = (game, player) => gsAxios.get('/rating/' + game + '/' + player);

export const addRating = (player, game, rating) => gsAxios.post('/rating/', {
    player, game, rating, ratedOn: formatDate(new Date()),
})