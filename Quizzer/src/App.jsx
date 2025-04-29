import React from 'react';
import { HashRouter, Route, Routes, Link } from 'react-router-dom';
import Container from "@mui/material/Container"
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography';
import CssBaseline from '@mui/material/CssBaseline';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';


import QuizList from './Components/QuizList';
import QuestionList from './Components/QuestionList';
import CategoryList from './Components/CategoryList';

function App() {
  const [value, setValue] = React.useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  }

  return (
    <HashRouter>
      <Container maxwidth="xl">
        <Box sx={{ color: 'primary.contrastText', p: 2 }}>
          <Typography variant="h4" color='black' paddingTop={1} align='center'>Quizzer</Typography>

          <Tabs value={value} onChange={handleChange} aria-label='navigation tabs' centered>
            <Tab 
            label="Quiz List" 
            component={Link} to='/' />
            <Tab 
            label="Question List" 
            component={Link} to='/questionlist' />
            <Tab 
            label="Categories" 
            component={Link} to='/categories' />
          </Tabs>
        </Box>

        <Routes>
          <Route path='/' element={<QuizList />} />
          <Route path='/questionlist' element={<QuestionList />} />
          <Route path='/categories' element={<CategoryList />} />
        </Routes>

        <CssBaseline />
      </Container>
    </HashRouter>
  )
}

export default App
