import { BrowserRouter, Route, Routes as Switch } from "react-router-dom";

import { Catalog } from "./pages/Catalog";
import { Login } from "./pages/Login";

export function Routes() {
    return (
        <BrowserRouter>
        <Switch>
                <Route path="/" element={<Login />} />
                <Route path="/catalog" element={<Catalog />} />
            </Switch>
        </BrowserRouter>
    )
}