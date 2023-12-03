import React,{useState} from 'react';
import { Box, IconButton, Stack, Typography, Button, Divider } from '@mui/material';
import { ArchiveBox, CircleDashed, MagnifyingGlass } from 'phosphor-react';
import { useTheme } from '@mui/material/styles';
import ChatElement from '../../components/ChatElement';
import { Search, SearchIconWrapper, StyledInputBase } from '../../components/Search';
import axios from 'axios'
import { useEffect } from 'react';

const Chats = ({ chatList, selectedChatIndex, onChatItemClick }) => {
 
    //get the first messages for any conversations
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [viewChatList, setViewChatList] = useState([]);

    // Fonction pour obtenir le nom de la conversation à partir du serveur
    const  getChatName=async (convId) =>{
      const apiUrl = `http://localhost:8080/api/conv/${convId}`;

      try {
        const response = await axios.get(apiUrl);
        return response.data.chatName;
      } catch (error) {
        console.error('Erreur lors de la récupération du nom de la conversation :', error);
        return 'Loading';
      }
    }

  

    useEffect(() => {

      const fetchData = async () => {
        try {
          // Démarrez le chargement
          setLoading(true);
          // Initialisez un objet pour suivre le premier message de chaque conversation
          const firstMessageByConvId = {};

          // Filtrez les messages pour ne garder que le premier message de chaque conversation
          chatList.forEach((message) => {
            if (!firstMessageByConvId[message.convId]) {
              firstMessageByConvId[message.convId] = message;
            }
          });
          // Créez la nouvelle liste en parallèle avec les requêtes au serveur pour obtenir les noms de conversation
          const result = await Promise.all(Object.keys(firstMessageByConvId).map(async (convId) => {
            const chatName = await getChatName(convId);
            return {
              id: firstMessageByConvId[convId].id,
              name: chatName,
              msg:firstMessageByConvId[convId].messageContent,
              time: firstMessageByConvId[convId].DateEnvoie,
              unread: 0,
              pinned: true,
              online: true,
            };
          }));

          // Mettez à jour l'état avec les résultats
          setViewChatList(result);
        } catch (error) {
          console.error('Erreur lors de la récupération des données :', error);

          // Stockez l'erreur dans l'état
          setError(error);
        } finally {
          // Arrêtez le chargement, quelle que soit l'issue
          setLoading(false);
        }
      };

      fetchData();
    }, [chatList]);




  const theme = useTheme();
  const [selectedOriginalIndex, setSelectedOriginalIndex] = useState(null);

  const handleChatItemClick = (originalIndex, index) => {
    onChatItemClick(index);
    setSelectedOriginalIndex(originalIndex);
  };


  return (
    <Box
      sx={{
        position: 'relative',
        width: 320,
        backgroundColor: theme.palette.mode === 'light' ? '#F8FAFF' : theme.palette.background.paper,
        boxShadow: '0px 0px 2px rgba(0,0,0,0.25)',
      }}
    >
      <Stack p={3} spacing={2} sx={{ height: '100vh' }}>
        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="h5">Chats</Typography>
          <IconButton>
            <CircleDashed />
          </IconButton>
        </Stack>

        <Stack sx={{ width: '100%' }}>
          <Search>
            <SearchIconWrapper>
              <MagnifyingGlass color="#709CE6" />
            </SearchIconWrapper>
            <StyledInputBase placeholder="Search..." inputProps={{ "aria-label": "search" }} />
          </Search>
        </Stack>

        <Stack spacing={1}>
          <Stack direction="row" alignItems="center" spacing={1.5}>
            <ArchiveBox size={24} />
            <Button>Archive</Button>
          </Stack>
          <Divider />
        </Stack>

        <Stack className="scrollbar" spacing={2} direction="column" sx={{ flexGrow: 1, overflow: 'scroll', height: '100%' }}>
          <Stack spacing={2.4}>
            <Typography variant="subtitle2" sx={{ color: '#676767' }}>
              Pinned
            </Typography>
            {chatList && viewChatList.filter((el) => el.pinned).map((el, index) => (
              <ChatElement
                key={el.id}
                {...el}
                selected={index === selectedChatIndex}
                onClick={() => handleChatItemClick(index, index)}
              />
            ))}
          </Stack>

          <Stack spacing={2.4}>
            <Typography variant="subtitle2" sx={{ color: '#676767' }}>
              All Chats
            </Typography>
            {chatList && viewChatList.filter((el) => !el.pinned).map((el, index) => (
              <ChatElement
                key={el.id}
                {...el}
                selected={index === selectedChatIndex}
                onClick={() => handleChatItemClick(index, index)}
              />
            ))}
          </Stack>
        </Stack>
      </Stack>
    </Box>
  );
};

export default Chats;
