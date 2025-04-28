import React from 'react';
import Container from "@mui/material/Container"
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography';
import { Link, Outlet } from 'react-router-dom';

import QuizList from './Components/QuizList';
import { AppBar, CssBaseline, Stack, Toolbar } from '@mui/material';

function App() {
  return (
    <>
      <CssBaseline />
      <Stack flexDirection="column">
        <AppBar position='static'>
          <Toolbar>
            <Typography variant="h4">Quizzer</Typography>
            <nav>
              <Link to={"/"}
                style={{
                  textDecoration: "none",
                  color: "white",
                  marginLeft: "20px",
                }}
              >QUIZZES</Link>

              <Link to={"categories"}
                style={{
                  textDecoration: "none",
                  color: "white",
                  marginLeft: "15px",
                }}
              >CATEGORIES</Link>
            </nav>
          </Toolbar>
        </AppBar>

      </Stack>
      <Outlet />

      <Box
        mt={3}
        ml={1}
        mr={1}
      >
        <Typography variant="h4">Quizzes</Typography>
      </Box>

      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        mt={3}
        ml={1}
        mr={1}
      >
        <QuizList />
      </Box>
    </>
  )
}

export default App
