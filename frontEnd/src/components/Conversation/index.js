import {  Box, Stack} from '@mui/material';
import React, { useContext, useState ,useEffect} from 'react';
import { useTheme } from "@mui/material/styles";
import Header from './Header';
import Footer from './Footer';
import Message from './Message';
import { AuthContext } from '../../contexts/AuthContext';
import axios from 'axios'

const Conversation = ({convId}) => {
    const theme = useTheme();
    const { currentUser } = useContext(AuthContext);
    const [conversation, setConversation] = useState(null);
  
    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/conv/${convId}`, {
            headers: {
              Authorization: 'Bearer ' + currentUser,
            },
          });
  
          setConversation(response.data);
        } catch (error) {
          console.error('Erreur lors de la récupération des chats :', error);
        }
      };
  
      fetchData();
    }, [currentUser, convId]);



  return (
    <Stack height={'100%'} maxHeight={'100vh'} width={'auto'}>

        {/* Chat header */}
        {
        conversation && <Header conversation={conversation}/>
        }
        {/* Msg */}
        <Box className='scrollbar' width={"100%"} sx={{flexGrow:1, height:'100%', overflowY:'scroll'}}>
        <Message menu={true} messages={conversation?.message}/>
        </Box>
        {/* Chat footer */}
        {
        //conversation && 
        <Footer convId={convId} />
        }
       
    </Stack>
  )
}

export default Conversation