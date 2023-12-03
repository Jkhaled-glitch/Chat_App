import { Avatar, Badge, Box, Stack, Typography } from '@mui/material';
import {useTheme , styled} from '@mui/material/styles';
import StyledBadge from './StyledBadge';

//single chat element
const ChatElement = ({ id, name, img, msg, time, online, unread, selected, onClick }) => {
  const theme = useTheme();

  return (
    <Box
      onClick={onClick}
      sx={{
        width: "100%",
        borderRadius: 1,
        backgroundColor: selected
          ? theme.palette.primary.main // Use the primary color for the selected element
          : theme.palette.mode === 'light' ? "#fff" : theme.palette.background.default,
      }}
      p={2}
    >
      <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Stack direction='row' spacing={3}>
            {online ? <StyledBadge overlap='circular' anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
              variant="dot">
            <Avatar  />
            </StyledBadge> : <Avatar /> }
            
            <Stack >
              <Typography variant='subtitle2'>
                {name}
              </Typography>
              <Typography variant='caption'>
                {msg}
              </Typography>
            </Stack>
            </Stack>
            <Stack spacing={1}  alignItems='center'>
              <Typography sx={{fontWeight:400}} variant='caption'>
                {time}
              </Typography>
              <Badge color='primary' badgeContent={unread}>
  
              </Badge>
            </Stack>
          
          
        </Stack>
  
  
      </Box>
    )
  };

  export default ChatElement