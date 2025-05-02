import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getQuizById, getQuestionsByQuizId } from "../utils/quizapi";
import { Typography } from "@mui/material";
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Item from '@mui/material/Grid';
import Checkbox from '@mui/material/Checkbox';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Button from '@mui/material/Button';


function QuestionList() {
  const quizId = useParams().quizId;
  const [quiz, setQuiz] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [error, setError] = useState(null);


  
  useEffect(() => {
    if (!quizId) {
      setError("Quiz ID is missing from URL.");
      return;
    }
    getQuizById(quizId)
      .then(data => {
        setQuiz(data);
        return getQuestionsByQuizId(quizId);
      })
      .then(questions => {
        setQuestions(questions);
        console.log(questions);
      })
      .catch((err) => setError(err.message));
  }, [quizId]);

  if (error) return <div>Error: {error}</div>;
  if (!quiz) return <div>Loading...</div>;

  return (
    <div className="ag-theme-material" style={{ width: "100%", height: 400 }}>

      <h1>{quiz.name}</h1>
      <h2>{quiz.description}</h2>
      <h4>Questions:</h4>
      <Box sx={{ width: "100%", height: 400 }}>
        {questions.length > 0 ? (
          questions.map((q,index) => (
            <Item sx={{ my: 1, mx: 'auto', p:1 }}key={q.id || q.questionId}>
              <Stack spacing={1} direction="row" sx={{ alignItems: "center" }} >
                <Typography variant="h6" color="black">
                  {index + 1}. {q.questionText || q.text}
                </Typography>
              </Stack>                
              <Typography variant="subtitle1" color="black">
                Question {index + 1} of {questions.length} - Difficulty: {q.difficulty}
              </Typography>
              <FormGroup>
              {q.answers.map((answer) => (
                  <FormControlLabel
                    key={answer.answerId}
                    control={<Checkbox />}
                    label={answer.text}
                  />
                ))}
              </FormGroup>
              <Button variant="contained" color="primary">
                Submit Answer
              </Button>
            </Item>
          ))
          ): (
            <li>No questions found.</li>
          )}
      </Box>
    </div>
  );
}

export default QuestionList;
