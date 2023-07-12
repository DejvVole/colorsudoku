function ColorMenu ({onFillTile}){

    const handleTileClick = (value) => {
        onFillTile(value); // odosle sa value to Tile.js
    }

    return (
        <div className='color-menu' >
            <div className={'1'} onClick={() => handleTileClick(`1`)}></div>
            <div className={'2'} onClick={() => handleTileClick(`2`)}></div>
            <div className={'3'} onClick={() => handleTileClick(`3`)}></div>
            <div className={'4'} onClick={() => handleTileClick(`4`)}></div>
            <div className={'5'} onClick={() => handleTileClick(`5`)}></div>
            <div className={'6'} onClick={() => handleTileClick(`6`)}></div>
            <div className={'7'} onClick={() => handleTileClick(`7`)}></div>
            <div className={'8'} onClick={() => handleTileClick(`8`)}></div>
            <div className={'9'} onClick={() => handleTileClick(`9`)}></div>
        </div>
    )
}

export default ColorMenu;