import { Avatar, Box, Typography,IconButton, Divider,Stack, } from '@mui/material'
import { CaretDown, MagnifyingGlass, Phone,VideoCamera } from 'phosphor-react'
import React, { useContext, useEffect, useState } from 'react';
import { useTheme } from "@mui/material/styles";
import { faker } from '@faker-js/faker';
import StyledBadge from '../StyledBadge';
import { ToggleSidebar } from '../../redux/slices/app';
import { useDispatch } from 'react-redux';
import { AuthContext } from '../../contexts/AuthContext';
import axios from 'axios'

const Header = ({conversation}) => {
  const dispatch = useDispatch();
  const {currentUser} = useContext(AuthContext)

  const isGroup = conversation?.participants.length > 2
  const theme = useTheme();
  const [user, setUser] = useState(null);
  const [oneParticipant, setOneParticipant] = useState(null);

useEffect(() => {
  const fetchData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/test/user/me", {
        headers: {
          'Authorization': 'Bearer ' + currentUser,
        }
      });

      if (response.status === 200) {
        setUser(response.data);

        if (!isGroup && conversation && conversation.participants) {
          // Use !== for inequality
          setOneParticipant(conversation.participants.filter(participant => participant.id !== user.id));
        }
      }
    } catch (error) {
      console.error("Erreur lors de la récupération de l'utilisateur connecté :", error);
    }
  };

  fetchData();
}, [currentUser, isGroup, conversation, user]);

 
  return (
    <Box p={2} sx={{ width:'100%', backgroundColor: theme.palette.mode === 'light' ? '#F8FAFF' : theme.palette.background.paper, boxShadow:'0px 0px 2px rgba(0,0,0,0.25)'}}>
    <Stack alignItems={'center'} direction='row' justifyContent={'space-between'}
    sx={{width:'100%', height:'100%'}}>
        <Stack onClick={()=>{
            dispatch(ToggleSidebar());
        }} direction={'row'} spacing={2}>
            <Box>
                <StyledBadge  overlap="circular"
                anchorOrigin={{ // position
                    vertical: "bottom",
                    horizontal: "right",
                }}
                variant="dot">
                    <Avatar alt={isGroup?conversation.chatName: oneParticipant?.firsName} />
                </StyledBadge>
                
            </Box>
            <Stack spacing={0.2}>
                    <Typography variant='subtitle2'>
                        { isGroup? conversation.chatName : oneParticipant?.lastName + " "+ oneParticipant && user.firstName
                        }
                    </Typography>
                    <Typography variant='caption'>
                        Online
                    </Typography>
                </Stack>
        </Stack>
        <Stack direction='row' alignItems='center' spacing={3}>
            <IconButton>
                <VideoCamera/>
            </IconButton>
            <IconButton>
                <Phone/>
            </IconButton>
            <IconButton>
                <MagnifyingGlass/>
            </IconButton>
            <Divider orientation='vertical' flexItem/>
            <IconButton>
                <CaretDown/>
            </IconButton>
        </Stack>
    </Stack>
</Box>
  )
}

export default Header