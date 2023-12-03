import { yupResolver } from '@hookform/resolvers/yup';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import * as Yup from 'yup';
import FormProvider from '../../components/hook-form/FormProvider';
import { Alert, Button, IconButton, InputAdornment, Stack } from '@mui/material';
import { RHFTextField } from '../../components/hook-form';
import { Eye, EyeSlash } from 'phosphor-react';
import axios from 'axios'
import { useNavigate } from 'react-router-dom';

const RegisterForm = () => {
  const navigate = useNavigate()

    const [showPassword, setShowPassword] = useState(false);

    //validation rules 
    const registerSchema = Yup.object().shape({
      firstName:Yup.string().required('First Name is required'),
      lastName:Yup.string().required('Last Name is required'),
      email:Yup.string().required('Email is required').email('Email must be a valid email address'),
      password:Yup.string().required('Password is required')
    });
  
    const defaultValues = {
      firstName:'',
      lastName:'',
      email:'',
      password:''
    };
  
    const methods = useForm({
      resolver: yupResolver(registerSchema),
      defaultValues
    });
  
    const {reset, setError, handleSubmit, formState:{errors, isSubmitting, isSubmitSuccessful}}
     = methods;
  
     const onSubmit = async (data) =>{
      const user = 
        {
         firstname: data.firstName,
         lastname: data.lastName,
         username: data.email,
         email: data.email, 
         password: data.password
        }
      
          try {
            console.log(user)
            const response = await axios.post("http://localhost:8080/api/auth/signup", user
           );

           // authentifier l'utilisateur crée
           const auth= await axios.post('http://localhost:8080/api/auth/signin', 
           {
            username: user.username,
            password: user.password
          });

           if(auth.status === 200){
            localStorage.setItem('token',auth.data.token)
             navigate('/app')
           }
           else{
            navigate('/auth/login')
           }
          
          } catch (error) {
              console.log("ERR" + error);  
              reset();
              setError('afterSubmit',{
                  ...error,
                  message: error.response.data.message
              })
              
          }
     }

  return (
    <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
        <Stack spacing={3}>
        {!!errors.afterSubmit && <Alert severity='error'>{errors.afterSubmit.message}</Alert>}
        <Stack direction={{xs:'column', sm:'row'}} spacing={2}>
            <RHFTextField name="firstName" label='First Name'/>
            <RHFTextField name="lastName" label='Last Name'/>
        </Stack>
        <RHFTextField name='email' label='Email address'/>
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
        <Button fullWidth color='inherit' size='large' type='submit' variant='contained'
        sx={{bgcolor:'text.primary', color:(theme)=> theme.palette.mode === 'light' ?
         'common.white':'grey.800',
         '&:hover':{
            bgcolor:'text.primary',
            color:(theme)=> theme.palette.mode === 'light' ? 'common.white':'grey.800',
         }}}>Create Account</Button>
        </Stack>
        
    </FormProvider>
  )
}

export default RegisterForm