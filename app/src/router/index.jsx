import { createBrowserRouter } from "react-router-dom";

import {
  Login,
  Profile,
  Learn,
  Activity,
  Question,
  ActivityResult,
  CreateUser,
  FindFriend,
  ForgotPassword,
  ResetPassword,
  EditProfile,
  Store,
  League,
  InsertQuestion,
  LeagueHistory,
  HelpLeague,
  ChallengeResult,
  HelpLearn,
} from "../ui/screens";

import { PrivateRoute } from "./private-route.component";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />,
  },
  {
    path: "/create",
    element: <CreateUser />,
  },
  {
    path: "/forgot-password",
    element: <ForgotPassword />,
  },
  {
    path: "/reset-password",
    element: <ResetPassword />,
  },
  {
    path: "/learn",
    element: <PrivateRoute Screen={Learn} />,
  },
  {
    path: "/learn/activity/:activityId",
    element: <PrivateRoute Screen={Activity} />,
  },
  {
    path: "/learn/activity/:activityId/practice",
    element: <PrivateRoute Screen={Question} />,
  },
  {
    path: "/learn/activity/:activityId/result",
    element: <PrivateRoute Screen={ActivityResult} />,
  },
  {
    path: "/profile",
    element: <PrivateRoute Screen={Profile} />,
  },
  {
    path: "/profile/find",
    element: <PrivateRoute Screen={FindFriend} />,
  },
  {
    path: "/profile/edit",
    element: <PrivateRoute Screen={EditProfile} />,
  },
  {
    path: "/store",
    element: <PrivateRoute Screen={Store} />,
  },
  {
    path: "/league",
    element: <PrivateRoute Screen={League} />,
  },
  {
    path: "/profile/insert-question",
    element: <PrivateRoute Screen={InsertQuestion} />,
  },
  {
    path: "/league/history",
    element: <PrivateRoute Screen={LeagueHistory} />,
  },
  {
    path: "/league/help",
    element: <PrivateRoute Screen={HelpLeague}  />,
  },
  {
    path: "/league/challenge",
    element: <PrivateRoute Screen={Question} />,
  },
  {
    path: "league/challenge/result",
    element: <PrivateRoute Screen={ChallengeResult} />,
  },
  {
    path: "/learn/help",
    element: <PrivateRoute Screen={HelpLearn}  />,
  },
]);
