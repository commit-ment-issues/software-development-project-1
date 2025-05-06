import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getQuizById, getQuestionsByQuizId, updateResults } from "../utils/quizapi";
import { CardActionArea, RadioGroup, Typography } from "@mui/material";
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Item from '@mui/material/Grid';
import Checkbox from '@mui/material/Checkbox';
import FormGroup from '@mui/material/FormGroup';
import FormControl from "@mui/material/FormControl";
import FormControlLabel from '@mui/material/FormControlLabel';
import FormLabel from "@mui/material/FormLabel";
import Button from '@mui/material/Button';
import Radio from "@mui/material/Radio";
import Card from "@mui/material/Card";
import CardContent from '@mui/material/CardContent';



function QuestionList() {
  const quizId = useParams().quizId;
  const [quiz, setQuiz] = useState([]);
  const [questions, setQuestions] = useState([]);
  const [error, setError] = useState(null);
  const [selectedAnswers, setSelectedAnswers] = useState({
    questionId: null,
    answerId: null,
    answerStatus: null
  });

  const handleChange = (e, questionId) => {
    const answerId = parseInt(e.target.value);
    const answer = questions
      .find(q => q.questionId === questionId)
      ?.answers.find(a => a.id === answerId);

    if (answer) {
      setSelectedAnswers({
        questionId: questionId,
        answerId: answerId,
        answerStatus: answer.status
      });
    }
  };

  const handleSubmit = (questionId) => {
    if (!selectedAnswers.answerId) {
        console.error("No answer selected");
        return;
    }

    updateResults(questionId, {
        questionId: questionId,
        answers: [{
            id: selectedAnswers.answerId,
            status: selectedAnswers.answerStatus
        }]
    })
    .then(response => {
        console.log("Answer submitted successfully", response);
        setSelectedAnswers({
            questionId: null,
            answerId: null,
            answerStatus: null
        });
        return getQuestionsByQuizId(quizId);
    })
    .then(updatedQuestions => {
        setQuestions(updatedQuestions);
    })
    .catch(error => {
      console.error("Error submitting answer:", error);
      setError("Failed to submit answer. Please try again.");
  });
};
  
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
        {questions.length > 0 ? (questions.map((q, index) => (
            <Card sx={{ maxWidth: 800, mb: 2 }} key={q.id || q.questionId}>
              <CardActionArea>
                <CardContent>
                  <Typography variant="h5">
                    {index + 1}. {q.questionText || q.text}
                  </Typography>
                  <Typography variant="subtitle1">
                  Question {index + 1} of {questions.length} - difficulty: {q.difficulty}
                  </Typography>
                </CardContent>
              </CardActionArea>
              <CardContent>
                <FormControl>
                  <RadioGroup
                    aria-labelledby={`radio-buttons-${q.questionId}`}
                    name={`question-${q.questionId}`}
                    value={(selectedAnswers.questionId === q.questionId && selectedAnswers.answerId) 
                      ? selectedAnswers.answerId.toString() 
                      : ''}
                    onChange={(e) => handleChange(e, q.questionId)}
                  >
                    {q.answers.map((answer) => (
                      <FormControlLabel 
                        key={answer.id}
                        value={answer.id}
                        control={<Radio />} 
                        label={answer.text} 
                      />
                    ))}
                  </RadioGroup>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleSubmit(q.questionId)}
                    sx={{ width: 200, mt: 2 }}
                  >
                    Submit answer
                  </Button>
                </FormControl>
              </CardContent>
            </Card>
          ))) : (
            <Typography variant="body1">No questions found.</Typography>
          )}
      </Box>
    </div>
  );
}

export default QuestionList;
