import { useNavigate } from 'react-router';

import { useForm } from 'react-hook-form';

import './styles.scss';
import { LoginApi } from '../../service/api';
import { useState } from 'react';
import { saveSessionData } from '../../context/auth';

type FormState = {
    username: string;
    password: string;
}

export function Card() {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [hasError, setHasError] = useState(false);

    const navigate = useNavigate();

    function onSubmit(data: FormState) {
        LoginApi(data)
            .then(response => {
                setHasError(false);
                saveSessionData(response.data);
                navigate("/catalog");
            })
            .catch(() => setHasError(true));
    }

    return (
        <div className="container-card card-base">
            <h1>LOGIN</h1>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="margin-bottom-20">
                    <input
                        type="email"
                        className={`input-text ${errors.username ? 'invalid' : ''}`}
                        placeholder="E-mail"
                        {
                        ...register("username",
                            {
                                required: "Campo obrigatorio",
                                pattern: {
                                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                    message: "E-mail inválido"
                                }
                            })
                        }
                    />
                    {
                        errors.username && (
                            <p className="info-invalid">
                                {errors.username.message}
                            </p>
                        )
                    }
                </div>

                <div>
                    <input
                        type="password"
                        className={`input-text ${errors.password ? 'invalid' : ''}`}
                        placeholder="Senha"
                        {
                        ...register("password",
                            {
                                required: "Campo obrigatorio",
                                minLength: {
                                    value: 5,
                                    message: "Sua senha é acima de 5 caracteres"
                                }
                            })
                        }
                    />
                    {
                        errors.password && (
                            <p className="info-invalid">
                                {errors.password.message}
                            </p>
                        )
                    }
                </div>

                {
                    hasError && (
                        <div className='containerError'>
                            Usuário ou senha inválidos!
                        </div>
                    )
                }

                <button
                    className="button-login"
                >
                    FAZER LOGIN
                </button>
            </form>
        </div>
    );
};