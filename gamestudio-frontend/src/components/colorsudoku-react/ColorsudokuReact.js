import "../css/ColorsudokuReact.css"
import {useEffect, useRef, useState} from "react";
import fieldService from "./_api/colorsudokuFieldService";
import Field from "./Field";

function ColorsudokuReact({handleWin}){
    //vytvorenie state
    const [field, setField] = useState(null);
    const [showNav, setShowNav] = useState(false);
    const [gameWon, setGameWon] = useState(false);

    const [previousRow, setPreviousRow] = useState(null);
    const [previousColumn, setPreviousColumn] = useState(null);

    const [tutorial, setTutorial] = useState(false);

    const [back, setBack] = useState(false);


    //ak sa zvoli obtieznost zavola sa tato funkcia a vytvori nove herne pole
    const handleChoosenDiff = (diff) => {
        setShowNav(true);
        fieldService.newGame(diff).then(response => {
            setField(response.data);
        });
    }

    const handleFillTile = (row, col, value) => {
        setPreviousRow(row);
        setPreviousColumn(col);
        if(field.gameState === "PLAYING"){
            fieldService.fillTile(row, col, value).then(response => {
                setField(response.data);
            });
        }
        setBack(false);
    }

    useEffect(() => {
        if (field && field.gameState === "SOLVED") {
            setGameWon(true);
            handleWin(field.score)
        }
    }, [field]); //useEffect kontroluje či sa zmenil field (hodnota tiles alebo gamestate), ak ano spusti sa kod v nom

    const handleDeleteTile = (row, col) => {
        if(field.gameState === "PLAYING") {
            fieldService.deleteTile(row, col).then(response => {
                setField(response.data);
            });
        }
    }

    const newGame = () => {
        setField(null);
        setShowNav(false);
        setGameWon(false);
    }

    const previousFill = () => {
        if(previousColumn != null && previousRow != null) {
            fieldService.deleteTile(previousRow, previousColumn).then(response => {
                setField(response.data);
            });
            setBack(true);
        }
    }

    const randomFill = () => {
        fieldService.randomFill().then(response => {
            setField(response.data);
        });
    }

    useEffect(() => {
        setTutorial(false);
    }, []);

    const handleToggleTutorial = () => {
        setTutorial(!tutorial);
    }

    return(
        <div className="game-container">
            {
                showNav ? (
                    showNav &&
                    <div>
                        <button className="button new-game-button" onClick={() => previousFill()}>BACK</button>
                        <button className="button new-game-button random-button" onClick={() => randomFill()}>RANDOM FILL</button>
                        <br/>
                        <button className="button new-game-button" onClick={() => newGame()}>NEW GAME</button>
                        {back && <div className="success">ROW {previousRow+1} COLUMN {previousColumn+1} WAS DELETED</div>}
                    </div>
                ) : (
                    <div>
                        <div className="diff-buttons">
                            <button className="button"  onClick={() => handleChoosenDiff(`49`)}>EASY</button>
                            <button className="button" onClick={() => handleChoosenDiff(`5`)}>MEDIUM</button>
                            <button className="button" onClick={() => handleChoosenDiff(`0`)}>HARD</button>
                            {/*<button className="button" onClick={() => handleChoosenDiff(`50`)}>TEST</button>*/}

                        </div>
                        <button className="button how-to-play" onClick={handleToggleTutorial}>HOW TO PLAY</button>
                    </div>

                )
            }

            {
                tutorial &&
                <div className="tutorial">
                    <button onClick={handleToggleTutorial}>X</button>
                    <p>
                        <ol>
                            <li>Color Sudoku is played on a 9x9 grid.</li>
                            <li>Instead of using numbers like traditional Sudoku, Color Sudoku uses nine colors.</li>
                            <li>Each row, column, and 3x3 box must contain one and only one instance of each color.</li>
                            <li>No color can be repeated within any row, column, or 3x3 box.</li>
                            <li>Use left click to select color.</li>
                            <li>Use right click to delete your choice.</li>
                        </ol>

                    </p>
                </div>
            }
            
            {/*podmienka - pole vykresli len ak existuje field*/}
            { field &&
                <div className={gameWon && "won-opacity"}>
                    <p className="actual-score">YOUR SCORE: {field.score}</p>

                    <Field
                        tiles={field.tiles}
                        onFillTile={handleFillTile}
                        onDeleteTile={handleDeleteTile}
                    />
                </div>

            }

            {
                gameWon &&  <div className="game-won">YOU WON! YOUR SCORE: {field.score} <br/> CHECK TOP SCORE</div>
            }
        </div>

    );
}

export default ColorsudokuReact;