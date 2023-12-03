
import { Box, Stack } from '@mui/material';
import io from 'socket.io-client'; // Import the socket.io-client library
import { DocMsg, LinkMsg, MediaMsg, ReplyMsg, TextMsg, TimeLine } from './MsgTypes';

const Message = ({ menu, messages }) => {
 
 

  // Function to check if two dates have the same day
  const isSameDay = (date1, date2) => {
    if (!date1 || !date2) {
      return false; // Handle the case where either date is null
    }

    const day1 = date1.getDate();
    const month1 = date1.getMonth();
    const year1 = date1.getFullYear();

    const day2 = date2.getDate();
    const month2 = date2.getMonth();
    const year2 = date2.getFullYear();

    return day1 === day2 && month1 === month2 && year1 === year2;
  };

  const myNewMessages = [];
  let currentDate = null;

  messages && messages.forEach((message, index) => {
    const dateString = message.DateEnvoie.split(' ').reverse().join('-');
    const messageDate = new Date(dateString);
    console.log(dateString, !isNaN(messageDate.getTime())); // Log the date string and its validity


      // Check if a divider is needed
      if (!currentDate || !isSameDay(currentDate, messageDate)) {
        myNewMessages.push({
          type: 'divider',
          text: dateString,
        });
        currentDate = messageDate;
      }

      // Create a new message object
      const newMessage = {
        type: 'msg',
        message: message.messageContent,
        incoming: false, // You need to set this based on your logic
        outgoing: message.sender.id === 'currentUserId', // Set this based on your logic
        subtype: 'text', // Set this based on your logic
      };

      myNewMessages.push(newMessage);
    
  });

  


  return (
    <Box p={3}>
      <Stack spacing={3}>
        {myNewMessages.map((el, index) => {
          switch (el.type) {
            case 'divider':
              return <TimeLine key={index} el={el} />;

            case 'msg':
              switch (el.subtype) {
                case 'img':
                  return <MediaMsg key={index} el={el} menu={menu} />;

                case 'doc':
                  return <DocMsg key={index} el={el} menu={menu} />;

                case 'link':
                  return <LinkMsg key={index} el={el} menu={menu} />;

                case 'reply':
                  return <ReplyMsg key={index} el={el} menu={menu} />;

                default:
                  return <TextMsg key={index} el={el} menu={menu} />;
              }
              break;

            default:
              return <></>;
          }
        })}
      </Stack>
    </Box>
  );
};

export default Message;
