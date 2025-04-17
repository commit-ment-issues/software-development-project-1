import React from 'react';
import Container from "@mui/material/Container"
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography';

import QuizList from './Components/QuizList';

function App() {
  return (
    <Container maxWidth="xl">
      <Box 
        display="flex" 
        flexDirection="column" 
        alignItems="center"
        justifyContent="center"
      >
      <Typography variant="h6" align="center">List of Quizzes</Typography>
          <QuizList />
      </Box>
    </Container>
  )
}

export default App
