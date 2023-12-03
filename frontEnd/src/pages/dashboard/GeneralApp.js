import React from "react";
import io from 'socket.io-client'; // Importez la bibliothèque socket.io-client
import Chats from "./Chats";
import { Box, Stack } from "@mui/material";
import Conversation from "../../components/Conversation";
import { useTheme } from "@mui/material/styles";
import Contact from "../../components/Contact";
import { useSelector } from "react-redux";
import SharedMessages from "../../components/SharedMessages";
import StarredMessages from "../../components/StarredMessages";
import axios from 'axios'
import { useState ,useEffect} from "react";
import { useContext } from 'react';
import { AuthContext } from '../../contexts/AuthContext';
import useChatList from '../../hooks/useChatList';

const GeneralApp = () => {
  const theme = useTheme();
  const {sidebar} = useSelector((store)=> store.app);// access our store inside component

  const {chatList} = useChatList();

  const [selectedChatIndex, setSelectedChatIndex] = useState(0);
  const [convId, setConvId] = useState('');


  const handleChatItemClick = (index, convId) => {
    setSelectedChatIndex(index);
    setConvId(convId);
  };


  const [receivedMessages, setReceivedMessages] = useState([]);
  
  useEffect(() => {
    // Connectez-vous au serveur WebSocket
    const socketInstance = io('ws://localhost:8080');
  
    socketInstance.on('sendMessage', (data) => {
      console.log('Received message from server:', data);
  
      // Mettez à jour l'état avec le nouveau message
      setReceivedMessages((prevMessages) => [...prevMessages, data]);
    });
  
    return () => {
      if (socketInstance) {
        socketInstance.disconnect();
      }
    };
  }, []); // Assurez-vous que ce tableau de dépendances est vide pour n'exécuter l'effet qu'une seule fois
  
  
  
  

  useEffect(() => {
    // Mettez à jour convId ici après avoir trié chatList
    setConvId(chatList[0]?.convId);
  }, [chatList]);

  return (
    <Stack direction='row' sx={{ width: '100%' }}>
      {/* Chats */}
      <Chats chatList={chatList} selectedChatIndex={selectedChatIndex} onChatItemClick={handleChatItemClick}/>

      <Box sx={{ height: '100%',
       width: sidebar.open ? 'calc(100vw - 740px)': 'calc(100vw - 420px)',
       backgroundColor: theme.palette.mode === 'light' ? '#F0F4FA' : theme.palette.background.default }}>
        {/* Conversation */}
        <Conversation convId={convId} receivedMessages={receivedMessages} />
      </Box>


      {/* Contact */}
      {sidebar.open && (()=>{
        switch (sidebar.type) {
          case 'CONTACT':
            return <Contact/>

          case 'STARRED':
            return <StarredMessages/>

          case 'SHARED':
            return <SharedMessages/>
        
          default:
            break;
        }
      })()  }
     
    </Stack>
  );
};

export default GeneralApp;
