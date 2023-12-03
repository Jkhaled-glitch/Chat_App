import { Link, Stack, Typography } from '@mui/material'
import React from 'react';
import { Link as RouterLink,useNavigate } from 'react-router-dom';
import AuthSocial from '../../sections/auth/AuthSocial';
import LoginForm from '../../sections/auth/LoginForm';
import { useEffect,useContext } from 'react';
import { AuthContext } from '../../contexts/AuthContext';



const Login = () => {

  const {currentUser} = useContext(AuthContext)
  const navigate = useNavigate();
  useEffect(()=>{
    //redirection if user connected
    if(currentUser){
      console.log(currentUser)
      navigate("/app");
    }
  },[])

  return (
    <>
    <Stack spacing={2} sx={{mb:5, position:'relative'}}>
      <Typography variant='h4'>
        Login to IWARE Chat
      </Typography>
      <Stack direction='row' spacing={0.5}>
        <Typography variant='body2'>New User?</Typography>
        <Link to='/auth/register' component={RouterLink} variant='subtitle2'>Create an account</Link>
      </Stack>
      {/* Login form */}
      <LoginForm/>
      {/* Auth Social */}
      <AuthSocial/>
    </Stack>
    </>
  )
}

export default Login