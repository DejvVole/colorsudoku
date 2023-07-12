import {useState} from "react";
import ColorMenu from "./ColorMenu";

function Tile({tile, onFillTile, onDeleteTile}){
    const [showMenu, setShowMenu] = useState(false);

    let tileClass;
    if("TO_GUESS".localeCompare(tile?.state) === 0){
        tileClass = tile.state.toLowerCase();
    }else if("WRONG_GUESS".localeCompare(tile?.state) === 0){
        tileClass = tile.state.toLowerCase();
    }else {
        tileClass = tile.value;
    }

    const handleTileClick = () => {
        setShowMenu(!showMenu);

    }

    const handleTileRightClick = (event) => {
        event.preventDefault(); // aby sa mi nezobrazovalo kontextove okno pri right kliku na stranke
        if(tile.state !== "TO_GUESS") {
            onDeleteTile();
        }
    }

    return (
        <td className={tileClass + " " + tile.value} onClick={handleTileClick} onContextMenu={handleTileRightClick}>

            {
                /*tu sa prijme z ColorMenu value do prveho "value" a odosle sa do fieldu*/
                /*plus osetrenuie ze colorMenu sa zobrazi iba ak to je to_guess tile*/
            }

            {
                tileClass === "TO_GUESS".toLowerCase() && showMenu && <ColorMenu onFillTile={(value) => onFillTile(value)}/>
            }
        </td>

    )
}

export default Tile;