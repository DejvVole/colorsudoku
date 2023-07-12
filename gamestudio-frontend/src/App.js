import './App.css';

import ColorsudokuReact from "./components/colorsudoku-react/ColorsudokuReact";
import {useEffect, useRef, useState} from "react";
import {addComment, fetchComments} from "./_api/comment.service"

import Comments from "./components/services/Comments";
import AddComment from "./components/services/AddComment";
import {addUser} from "./_api/registeruser.service";
import RegisterUser from "./components/services/RegisterUser";
import Login from "./components/services/Login";
import Cookies from "universal-cookie/es6";

import {Link, Route, Routes, useLocation, useNavigate} from "react-router-dom";
import {NavLink} from "react-router-dom";

import React from 'react';
import {addScore, fetchScore} from "./_api/score.service";
import Score from "./components/services/Score";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faArrowRightFromBracket, faStar} from "@fortawesome/free-solid-svg-icons";
import { faReact } from "@fortawesome/free-brands-svg-icons";

import {addRating, fetchAverageRating, fetchRating} from "./_api/rating.service";

import ReactStars from "react-rating-stars-component";
import {faUser} from "@fortawesome/free-regular-svg-icons";

function App() {
    const cookies = new Cookies();

    const selectedGame = 'colorsudoku';

    const [comments, setComments] = useState([]);
    const [score, setScore] = useState([]);
    const [rating, setRating] = useState([]);
    const [averageRating, setAverageRating] = useState([]);

    const [ratingSaved, setRatingSaved] = useState(false);

    const commentsRef = useRef(null);
    const scoresRef = useRef(null);

    const handleCommentsLinkClick = () => {
        commentsRef.current.scrollIntoView({ behavior: "smooth" });
    };

    const handleScoreLinkClick = () => {
        scoresRef.current.scrollIntoView({ behavior: "smooth" });
    };

    const fetchData = () => {
        fetchComments(selectedGame).then(response => {
            setComments(response.data);
        });

        fetchScore(selectedGame).then(response =>{
           setScore(response.data);
        });

        fetchAverageRating(selectedGame).then(response => {
            setAverageRating(response.data);
        })

        if(cookies.get('username')){
            fetchRating(selectedGame, cookies.get('username')).then(response => {
                setRating(response.data);
            })
        }
    }

    useEffect(() =>{
        fetchData();
    }, []);

    const handleComment = (comment) => {
        addComment(selectedGame, cookies.get('username'), comment).then(response => {
            fetchData();
        });
    }

    const handleScore = (score) => {
        addScore(selectedGame, cookies.get('username'), score).then(response => {
            fetchData();
        });
    }

    const handleUser = (user, hashedPasswd) => {
        addUser(user.username, hashedPasswd);
    }

    const handleLogin = (user) => {
        cookies.set('username', user.username);
    }

    const ratingChanged = (newRating) => {
        addRating(cookies.get('username'), selectedGame, newRating);
        setRatingSaved(!ratingSaved);
    };

    const [navBlack, setNavBlack] = useState(false);

    const location = useLocation();
    const navigate = useNavigate();


    const logout = () => {
        cookies.remove('username');
        navigate("/");
    }

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, []);

    const handleScroll = () => {
        if (window.pageYOffset > 90) {

            setNavBlack(true);
        } else {
            setNavBlack(false);
        }
    };

    const [showTutorial, setShowTutorial] = useState(false);

    return (
      <div className="app" >
          {/*<Navigation handleCommentsLinkClick={handleCommentsLinkClick} handleScoreLinkClick={handleScoreLinkClick}/>*/}

          <nav className={navBlack ? 'app-nav app-nav-black' : 'app-nav'} >
              <div className="app-nav-left">
                  <Link className={navBlack ? 'home-page link-black' : 'home-page link'} to="/">COLORSUDOKU</Link>

                  {location.pathname === "/" && (
                      <Link to="/" onClick={handleCommentsLinkClick} className={navBlack ? 'app-nav-lef-link link-black' : 'app-nav-lef-link link'}>
                          COMMENTS
                      </Link>
                  )}

                  {location.pathname === "/" && (
                      <Link to="/" onClick={handleScoreLinkClick} className={navBlack ? 'app-nav-lef-link link-black' : 'app-nav-lef-link link'}>
                          SCORE
                      </Link>
                  )}
              </div>


              <div className="user">
                  {   cookies.get("username") ? (
                      <React.Fragment>
                          <div
                              className={navBlack ? 'logged-player link-black' : 'logged-player link'}>
                              <FontAwesomeIcon className="fa-user" icon={faUser}/>
                              { cookies.get('username')}
                          </div>

                          <button className={navBlack ? 'logout-button-black' : 'logout-button'} onClick={logout}><FontAwesomeIcon className="fa-arrow" icon={faArrowRightFromBracket}/></button>
                      </React.Fragment>
                  ) : (
                      <React.Fragment>
                          <Link className={navBlack ? 'login-link link-black' : 'login-link link'} to="/login">LOGIN</Link>
                      </React.Fragment>

                  )}
              </div>
          </nav>

          <div className="app-main">
              <Routes>
                  <Route path={"login"} element={
                      <React.Fragment>
                          <div className="login-page">
                              <Login onLogin={handleLogin}/>
                              <NavLink className="dont-have-account link" to="/register">DON'T HAVE AN ACCOUNT? REGISTER</NavLink>
                          </div>
                      </React.Fragment>

                  }/>

                  <Route path={"register"} element={
                      <React.Fragment>
                          <div className="register-page">
                              <RegisterUser onRegister={handleUser}/>
                          </div>
                      </React.Fragment>

                  }/>

                  <Route index element={
                      <React.Fragment>
                          { cookies.get('username')  ? (

                                  <ColorsudokuReact handleWin={handleScore} />

                                  // <ColorsudokuReact handleWin={handleScore}/>
                              ):(
                                  <p className="log-to-play">PLEASE LOG IN TO PLAY!</p>
                              )
                          }

                          <div className="average-rating">
                              {/*;
                                Vytvorí array z dĺžky averageRating. Podčiarkovník v map znamené, že
                                prvý parameter nepotrebujeme.
                              */}
                              <p>AVERAGE RATING</p>
                              {Array.from({ length: averageRating }).map((_, index) => (
                                  <FontAwesomeIcon key={index} icon={faStar} className="average-rating-stars"/>
                              ))}
                          </div>

                          {
                              cookies.get('username') &&
                              <div>
                                  <p className="rate-game">RATE THIS GAME</p>
                                  <div className="react-stars">
                                      <ReactStars
                                          count={5}
                                          onChange={ratingChanged}
                                          size={24}
                                          char={<FontAwesomeIcon icon={faStar} />}
                                          activeColor="black"
                                      />
                                  </div>
                                  {ratingSaved && <div className="success">THANKS FOR RATING</div>}
                              </div>
                          }


                          <div className="line-first"></div>

                          <div ref={commentsRef}>
                              <Comments comments={comments} />
                          </div>

                          <div className="add-comment">
                              {   cookies.get("username") ? (
                                  <AddComment onSendComment={handleComment}/>
                              ) : (
                                  <p className="add-comment-logged">ONLY LOGGED USER CAN ADD COMMENT</p>
                              )}
                          </div>

                          <div className="line"></div>

                          <div ref={scoresRef}>
                              <Score score={score}/>
                          </div>


                      </React.Fragment>

                  }/>

              </Routes>

              <div className="line"></div>
          </div>

          <footer>
              <FontAwesomeIcon className="fa-react" icon={faReact}/>
              <div className="athor">&copy; DÁVID HIŠČÁR</div>
          </footer>
      </div>
    );
}

export default App;
