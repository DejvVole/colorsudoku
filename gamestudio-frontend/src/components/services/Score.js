import "../css/Score.css"
import React from "react";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faMedal, faRankingStar } from "@fortawesome/free-solid-svg-icons";

function Score({score}){
    let counter = 1;
    return(
        <div className="score-container">
            <h1>TOP SCORE</h1>
            <div className="table">
                <table className="score-table">
                    <thead>
                    <tr>
                        <th><FontAwesomeIcon  icon={faRankingStar}/></th>
                        <th>PLAYER</th>
                        <th>SCORE</th>
                        <th>DATE</th>
                    </tr>
                    </thead>
                    <tbody>
                    {score.map(score => (
                        <tr key={`score-${score.ident}`}>
                            <th>
                                {counter === 1 ? (
                                    <FontAwesomeIcon className="gold" icon={faMedal} {...counter++}/>
                                ) : counter === 2 ? (
                                    <FontAwesomeIcon className="silver" icon={faMedal} {...counter++}/>
                                ) : counter === 3 ? (
                                    <FontAwesomeIcon className="bronze" icon={faMedal} {...counter++}/>
                                ) : (
                                    counter++ + "."
                                )}
                            </th>
                            <th>{score.player}</th>
                            <th>{score.points}</th>
                            <th>{new Date(score.playedAt).toLocaleDateString()}</th>
                        </tr>
                    ))}

                    </tbody>
                </table>
            </div>

        </div>

    );
}

export default Score;