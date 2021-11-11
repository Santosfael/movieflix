import { Route, Navigate } from "react-router-dom";
import { isAllowedByRole, isAuthenticated, Role } from "../context/auth";

type Props = {
    children: React.ReactNode;
    redirectTo: string;
    allowedRoutes?: Role[];
}

export function PrivateRoute({ redirectTo, allowedRoutes }: Props) {
    if (!isAuthenticated()) {
        return (
            <Navigate to={redirectTo} />
        )
    } else if (isAuthenticated() && !isAllowedByRole(allowedRoutes)) {
        return (
            <Navigate to={'/catalog'} />
        )
    }
}