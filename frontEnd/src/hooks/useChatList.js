// useChatList.js
import { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../contexts/AuthContext';


const useChatList = () => {
  const { currentUser } = useContext(AuthContext);
  const [chatList, setChatList] = useState([]);

  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/messages/me', {
          headers: {
            Authorization: 'Bearer ' + currentUser,
          },
        });

        if (response.status === 200) {
          const sortedChatList = response.data.sort(
            (message1, message2) => new Date(message1.DateEnvoie) - new Date(message2.DateEnvoie)
          );
          setChatList(sortedChatList);
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des chats :', error);
      }
    };

    fetchData();
  }, [currentUser]);

  return { chatList };
};

export default useChatList;
