import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getQuizById, getQuestionsByQuizId, updateResults } from "../utils/quizapi";
import { CardActionArea, RadioGroup, Snackbar, Typography } from "@mui/material";
import Box from '@mui/material/Box';
import FormControl from "@mui/material/FormControl";
import FormControlLabel from '@mui/material/FormControlLabel';
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
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  
  
  const handleClose = () => {
    setOpen(false);
  }

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
    console.log(selectedAnswers)
  };

  const handleSubmit = (questionId) => {
      if (!selectedAnswers.answerId) {
          console.error("No answer selected");
          return;
      }

      if(selectedAnswers.answerStatus == 1){
        setMessage("That is correct, good job!")
      }else{
        setMessage("That is not correct, try again")
      }

      setOpen(true);
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

  const handleFetch = (quizId) => {
    if (!quizId) {
      setError("Quiz ID is missing from URL.");
      return;
    }
    getQuizById(quizId)
      .then(data => {
        setQuiz(data);
        setQuestions(data.questions);
      })
      .catch((err) => setError(err.message));
  }
  
  useEffect(() => {
    handleFetch(quizId);
  }, [quizId]);

  if (error) return <div>Error: {error}</div>;
  if (!quiz) return <div>Loading...</div>;

  return (
    <div className="ag-theme-material" style={{ width: "100%", height: 400 }}>

      <Typography variant='h4'>{quiz.name}</Typography>
      <Typography variant='h6'>{quiz.description}</Typography>
      <h4>Questions:</h4>
      <Box sx={{ width: "100%", height: 400 }}>
        {questions.length > 0 ? (questions.map((q, index) => (
            <Card sx={{ background: 'lightgray', maxWidth: '100%', mb: 2 }} key={q.id || q.questionId}>
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
            <Typography variant="body1">Loading...</Typography>
          )}
      </Box>
      <Snackbar
        open={open}
        autoHideDuration={2000}
        onClose={handleClose}
        message={message}
      />
    </div>
  );
}

export default QuestionList;
