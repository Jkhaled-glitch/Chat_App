import React , { useState } from 'react';
import * as Yup from 'yup';
import { useForm } from 'react-hook-form';
import FormProvider from '../../components/hook-form/FormProvider'
import { yupResolver } from '@hookform/resolvers/yup';
import { Alert, Button, IconButton, InputAdornment, Link, Stack } from '@mui/material';
import { RHFTextField } from '../../components/hook-form';
import { Eye, EyeSlash } from 'phosphor-react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../../contexts/AuthContext';
import { useContext } from 'react';

const LoginForm = () => {

  const { dispatch } = useContext(AuthContext);
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  //validation rules 
  const loginSchema = Yup.object().shape({
    username:Yup.string().required('Email is required').email('Email must be a valid email address'),
    password:Yup.string().required('Password is required')
  });

  const defaultValues = {
    username:'',
    password:''
  };

  const methods = useForm({
    resolver: yupResolver(loginSchema),
    defaultValues
  });

  const {reset, setError, handleSubmit, formState:{errors}}
   = methods;

   const onSubmit = async (data) =>{
        try {
          console.log(data)
          const response= await axios.post('http://localhost:8080/api/auth/signin', data);
          
          if(response.status === 200){
            //save the authentification user  
           dispatch( { type: "LOGIN", payload: response.data.token } );
           navigate("/app")
      
          }
        } catch (error) {
            console.log(error);
            setError('afterSubmit',{
                ...error,
                message: error.response.data.message
            })
        }
   }

  return (
    <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
        <Stack spacing={6}>
            {!!errors.afterSubmit && <Alert severity='error'>{errors.afterSubmit.message}</Alert>}
        
        <RHFTextField name='username' label='Email address'/>
        <RHFTextField name='password' label='Password' type={showPassword ? 'text' : 'password'}
        InputProps={{endAdornment:(
            <InputAdornment>
            <IconButton onClick={()=>{
                setShowPassword(!showPassword);
            }}>
                {showPassword ? <Eye/>: <EyeSlash/>}
            </IconButton>
            </InputAdornment>
        )}}/>
        </Stack>
        <Stack alignItems={'flex-end'} sx={{my:2}}>
            <Link component={RouterLink} to='/auth/reset-password'
             variant='body2' color='inherit' underline='always'>Forgot Password?</Link>
        </Stack>
        <Button fullWidth color='inherit' size='large' type='submit' variant='contained'
        sx={{bgcolor:'text.primary', color:(theme)=> theme.palette.mode === 'light' ?
         'common.white':'grey.800',
         '&:hover':{
            bgcolor:'text.primary',
            color:(theme)=> theme.palette.mode === 'light' ? 'common.white':'grey.800'
        }}}>Login</Button>
    </FormProvider>
  )
}

export default LoginForm