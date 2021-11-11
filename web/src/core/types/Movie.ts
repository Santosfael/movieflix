export type MovieResponse = {
    content: Movie[];
    totalPages: number;
}

export type Movie = {
    id: number;
    title: string;
    subtitle: string;
    year: number;
    genreId: Genre;
    imgUrl: string;

}

export type Genre = {
    id: number;
    name: string;
}