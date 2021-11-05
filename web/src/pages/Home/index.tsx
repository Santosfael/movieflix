import { Header } from "../../components/Header";

import logoImg from '../../assets/Desenho.svg';
import './styles.scss';
import { Card } from "../../components/Card";

export function Home() {
    return (
        <div>
            <Header />
            <div className="container-home">
                <div className="content-image">
                    <h1>Avalie Filmes</h1>
                    <p>Diga o que você achou do seu<br></br>filme favorito</p>
                    <img src={logoImg} alt="Logo desenho" />
                </div>
                <Card />
            </div>
        </div>
    );
};