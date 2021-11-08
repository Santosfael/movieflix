import axios, { AxiosRequestConfig } from "axios";

type LoginData = {
    username: string;
    password: string;
}

const BASE_URL = process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';


axios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    if(error.response.status === 401) {
        //logout();
    }

    return Promise.reject(error);
});

export function Api(params: AxiosRequestConfig) {
    return axios({
        ...params,
        baseURL: BASE_URL,
    });
};

export function PrivateRequestApi(params: AxiosRequestConfig) {
    //const sessionData = getSessionData();
}