import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Header } from '../../core/components/Header';
import { PrivateRequestApi } from '../../core/service/api';
import { Genre, MovieResponse } from '../../core/types/Movie';
import { MovieCard } from './components/MovieCard';
import './styles.scss';

export function Catalog() {
    const [movieResponse, setMovieResponse] = useState<MovieResponse>();
    const [activePage, setActivePage] = useState(0);
    const [genre, setGenre] = useState<Genre>();

    const getMovies = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 12,
            genreId: genre?.id,
        }
        PrivateRequestApi({
            url: '/movies',
            method: 'GET',
            params
        }).then(response => setMovieResponse(response.data));

    }, [activePage, genre]);

    useEffect(() => {
        getMovies();
    }, [getMovies]);

    return (
        <div>
            <Header />
            <div className='catalog-container'>
                <div className='catalog-movie'>
                    {
                        movieResponse?.content.map(movie => {

                            <MovieCard key={movie.id} movie={movie} />

                        })
                    }
                </div>
            </div>
        </div>
    );
};