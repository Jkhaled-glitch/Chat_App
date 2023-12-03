import { Suspense, lazy } from "react";// use to loading , loading screen until full page is load
import { Navigate, useRoutes } from "react-router-dom";
import { useContext } from "react";

// layouts
import DashboardLayout from "../layouts/dashboard";
import MainLayout from "../layouts/main";

// config
import { DEFAULT_PATH } from "../config";
import LoadingScreen from "../components/LoadingScreen";
import { AuthContext } from "../contexts/AuthContext";

//import Settings from "../pages/dashboard/Settings";

const Loadable = (Component) => (props) => {
  return (
    <Suspense fallback={<LoadingScreen />}> 
      <Component {...props} />
    </Suspense>
  );
};



export default function Router() {

  const { currentUser } = useContext(AuthContext);

  const RequireAuth = ({ children }) => {
    return currentUser ? children : <Navigate to="/auth/login" />;
  };


  return useRoutes([
    {
      path: '/auth',
      element: <MainLayout/>,
      children:[
        //recomended to add default path
        { element: <Navigate to={DEFAULT_PATH} replace />, index: true },
        {element: <LoginPage/>,  path:'login'},
        {element:  <RegisterPage/>, path:'register'},
        {element:  <ResetPasswordPage/>, path:'reset-password'},
        {element:  <NewPasswordPage/>, path:'new-password'},
        { path: "*", element: <Page404 />},

      ]
    },
    {
      path: "/",
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to={DEFAULT_PATH} replace />, index: true },
        { path: "app", element: <RequireAuth> <GeneralApp /> </RequireAuth> },
        { path: "settings", element: <RequireAuth><Settings /></RequireAuth> },
        { path: "group", element: <RequireAuth><GroupPage /></RequireAuth> },
        { path: "call", element: <RequireAuth><CallPage /></RequireAuth> },
        { path: "profile", element: <RequireAuth><ProfilePage /></RequireAuth> },
        //{ path: "404", element: <Page404 /> },
        { path: "*", element: <Page404 /> },
      ],
    },
    {
      path: "/404",
      element: <Page404 />,
    },
    { path: "*", element:  <Page404 />  },
  ]);
}

const GeneralApp = Loadable(
  lazy(() => import("../pages/dashboard/GeneralApp")),
);

const LoginPage = Loadable(
  lazy(() => import("../pages/auth/Login")),
);

const RegisterPage = Loadable(
  lazy(() => import("../pages/auth/Register")),
);

const ResetPasswordPage = Loadable(
  lazy(() => import("../pages/auth/ResetPassword")),
);

const NewPasswordPage = Loadable(
  lazy(() => import("../pages/auth/NewPassword")),
);

const GroupPage = Loadable(
  lazy(() => import("../pages/dashboard/Group")),
);

const Settings = Loadable(
  lazy(() => import("../pages/dashboard/Settings")),
);

const CallPage = Loadable(
  lazy(() => import("../pages/dashboard/Call")),
);

const ProfilePage = Loadable(
  lazy(() => import("../pages/dashboard/Profile")),
);
const Page404 = Loadable(lazy(() => import("../pages/Page404")));
