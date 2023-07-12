import gsAxios from "../../../_api";

const FIELD_URL = '/colorsudoku/field';
const NEW_GAME_URL = FIELD_URL + '/newgame';
const FILL_TILE_URL = FIELD_URL + '/filltile';
const DELETE_TILE_URL = FIELD_URL + '/deletetile';
const RANDOM_FILL = FIELD_URL + '/randomfill';

const fetchField = () => gsAxios.get(FIELD_URL);
const newGame = (diff) => gsAxios.get(`${NEW_GAME_URL}?diff=${diff}`);
const fillTile = (row, col, val) => gsAxios.get(`${FILL_TILE_URL}?row=${row}&col=${col}&val=${val}`);

const randomFill = () => gsAxios.get(RANDOM_FILL);
const deleteTile = (row, col) => gsAxios.get(`${DELETE_TILE_URL}?row=${row}&col=${col}`);

const fieldService = {fetchField, newGame, fillTile, deleteTile, randomFill}
export default fieldService; //exportujem jeden objekt fieldService miesto toho aby som neexportoval vsetko osobitne