import { Card } from '../../core/components/Card';
import { Header } from '../../core/components/Header';

import logoImg from '../../core/assets/images/desenho.svg';
import './styles.scss';

export function Login() {

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