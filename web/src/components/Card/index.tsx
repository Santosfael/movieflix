import './styles.scss';

export function Card() {
    return (
        <div className="container-card card-base">
            <h1>LOGIN</h1>
            <form>
                <input
                    className="input-text"
                    placeholder="E-mail"
                    type="email"
                />

                <input
                    className="input-text"
                    placeholder="Senha"
                    type="password"
                />

                <button
                    className="button-login"
                    type="submit"
                    onClick={() => { }}
                >
                    FAZER LOGIN
                </button>
            </form>
        </div>
    );
};