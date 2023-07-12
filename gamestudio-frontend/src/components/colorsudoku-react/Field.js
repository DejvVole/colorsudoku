import Tile from "./Tile";

function Field({tiles, onFillTile, onDeleteTile}){
    return(
        <table className="gamefield-table">
            <tbody>
                {tiles.map((row, rowIndex) => (
                    <tr key = {`row-${rowIndex}`}>
                        {row.map((tile, colIndex) => (
                            <Tile key={`tile-${rowIndex}-${colIndex}`}
                                  row={rowIndex}
                                  col={colIndex}
                                  tile={tile}
                                  // znova sa prijme pomocou prveho "value" z Tile.js a odosel sa dalej
                                  onFillTile={(value) => onFillTile(rowIndex, colIndex, value) }
                                  onDeleteTile={() => onDeleteTile(rowIndex, colIndex)}
                            />
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

export default Field;