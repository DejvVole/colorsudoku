import {Link, useLocation, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from "@fortawesome/free-regular-svg-icons";
import {faArrowRightFromBracket} from "@fortawesome/free-solid-svg-icons";
import Cookies from "universal-cookie/es6";

function Navigation({handleCommentsLinkClick, handleScoreLinkClick}){
    // const [navBlack, setNavBlack] = useState(false);
    //
    // const cookies = new Cookies();
    // const location = useLocation();
    // const navigate = useNavigate();
    //
    //
    // const logout = () => {
    //     cookies.remove('username');
    //     navigate("/");
    // }
    //
    // useEffect(() => {
    //     window.addEventListener('scroll', handleScroll);
    //     return () => {
    //         window.removeEventListener('scroll', handleScroll);
    //     };
    // }, []);
    //
    // const handleScroll = () => {
    //     if (window.pageYOffset > 90) {
    //
    //         setNavBlack(true);
    //     } else {
    //         setNavBlack(false);
    //     }
    // };

    //return(
        // <nav className={navBlack ? 'app-nav app-nav-black' : 'app-nav'} >
        //     <div className="app-nav-left">
        //         <Link className={navBlack ? 'home-page link-black' : 'home-page link'} to="/">COLORSUDOKU</Link>
        //
        //         {location.pathname === "/" && (
        //             <Link to="/" onClick={handleCommentsLinkClick} className={navBlack ? 'app-nav-lef-link link-black' : 'app-nav-lef-link link'}>
        //                 COMMENTS
        //             </Link>
        //         )}
        //
        //         {location.pathname === "/" && (
        //             <Link to="/" onClick={handleScoreLinkClick} className={navBlack ? 'app-nav-lef-link link-black' : 'app-nav-lef-link link'}>
        //                 SCORE
        //             </Link>
        //         )}
        //     </div>
        //
        //
        //     <div className="user">
        //         {   cookies.get("username") ? (
        //             <React.Fragment>
        //                 <Link
        //                     to={"/" + cookies.get('username')}
        //                     className={navBlack ? 'logged-player link-black' : 'logged-player link'}>
        //                     <FontAwesomeIcon className="fa-user" icon={faUser}/>
        //                     { cookies.get('username')}
        //                 </Link>
        //
        //                 <button className={navBlack ? 'loggout-button-black' : 'loggout-button'} onClick={logout}><FontAwesomeIcon className="fa-arrow" icon={faArrowRightFromBracket}/></button>
        //             </React.Fragment>
        //         ) : (
        //             <React.Fragment>
        //                 <Link className={navBlack ? 'login-link link-black' : 'login-link link'} to="/login">LOGIN</Link>
        //             </React.Fragment>
        //
        //         )}
        //     </div>
        // </nav>
    //)
}

export default Navigation;