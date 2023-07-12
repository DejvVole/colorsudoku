import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchUser = username => gsAxios.get('/login/' + username);

export const addUser = (username, password) => gsAxios.post('/login/',{
    username, password, loggedAt: formatDate(new Date()),
})