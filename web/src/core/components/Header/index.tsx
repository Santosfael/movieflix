import { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';

import { getAccessTokenDecoded, logout } from '../../context/auth';
import './styles.scss';

export function Header() {
    const [currentUser, setCurrentUser] = useState('');
    const location = useLocation();

    useEffect(() => {
        const currentUserData = getAccessTokenDecoded();
        setCurrentUser(currentUserData.user_name);
    }, [location]);

    function handleLogout() {
        logout();
    }

    return (
        <div className="container">
            <h1>MovieFlix</h1>
            {
                currentUser && (
                    <Link to={"/"} className='buttonLogout' onClick={handleLogout}>
                        LOGOUT
                    </Link>
                )
            }
        </div>
    )
}