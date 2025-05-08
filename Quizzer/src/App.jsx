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
import CategoryPage from './Components/CategoryPage';
import ResultsPage from './Components/ResultsPage';
import AddReview from './Components/AddReview';
import ReviewList from './Components/ReviewList';

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
            label="Quizzes" 
            component={Link} to='/' />
            <Tab 
            label="Categories" 
            component={Link} to='/categories' />
          </Tabs>
        </Box>

        <Routes>
          <Route path='/' element={<QuizList />} />
          <Route path='/quiz/:quizId/questions' element={<QuestionList />} />
          <Route path='/quiz/:quizId/results' element= {<ResultsPage />} />
          <Route path='/categories' element={<CategoryList />} />
          <Route path='/categories/:categoryid' element={<CategoryPage />} />
          <Route path='/quiz/:quizId/addreview' element={<AddReview />} />
          <Route path='/quiz/:quizId/reviewlist' element={<ReviewList/>} />
        </Routes>

        <CssBaseline />
      </Container>
    </HashRouter>
  )
}

export default App
