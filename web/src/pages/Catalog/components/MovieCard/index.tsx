import { Movie } from '../../../../core/types/Movie';

import './styles.scss';
type MovieProps = {
    movie: Movie;
}

export function MovieCard({ movie }: MovieProps) {
    return (
        <div className="card-base border-radius-10 movie-card">
            <img src={movie.imgUrl} alt={movie.title} className='movie-card-img' />
            <div className="movie-info">
                <h6 className="movie-title">
                    {movie.title}
                </h6>
                <h4 className='movie-year'>
                    {movie.year}
                </h4>
                <p className="movie-subtitle">
                    {movie.title}
                </p>
            </div>
        </div>
    )
}