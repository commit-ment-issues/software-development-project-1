import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Box, Typography, Divider } from "@mui/material";
import { getQuizById, getQuestionsByQuizId } from "../utils/quizapi"; // Muista importata getQuizById ja getQuestionsByQuizId
import { Link } from "react-router-dom"; // Lisää Link import

export default function QuestionList() {
  const { id } = useParams(); // Saamme quizId URL:sta
  const [quiz, setQuiz] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(true); // Ylläpidetään lataustilaa
  const [error, setError] = useState(null); // Ylläpidetään virheilmoitusta

  useEffect(() => {
    // Hakee quizin tiedot
    getQuizById(id)
      .then((data) => {
        setQuiz(data);
        return getQuestionsByQuizId(id); // Hakee kysymykset samalla
      })
      .then((data) => {
        setQuestions(data);
        setLoading(false); // Lataus valmis
      })
      .catch((error) => {
        setError("An error occurred while fetching the quiz data.");
        setLoading(false); // Lataus valmis vaikka virhe
      });
  }, [id]);

  // Jos on lataus
  if (loading) {
    return <Typography>Loading...</Typography>;
  }

  // Jos virhe
  if (error) {
    return <Typography color="error">{error}</Typography>;
  }

  // Jos quiz ei ole löytynyt
  if (!quiz) {
    return <Typography>Quiz not found.</Typography>;
  }

  return (
    <Box sx={{ maxWidth: "800px", mx: "auto", mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        {quiz.name}
      </Typography>

      <Typography variant="body1" gutterBottom>
        {quiz.description}
      </Typography>

      <Divider sx={{ my: 2 }} />

      <Typography variant="body2">
        <strong>Added on:</strong> {quiz.creationDate}
      </Typography>
      <Typography variant="body2">
        <strong>Questions:</strong> {questions.length}
      </Typography>
      <Typography variant="body2">
        <strong>Course code:</strong> {quiz.courseCode}
      </Typography>
      <Typography variant="body2" gutterBottom>
        <strong>Category:</strong> {quiz.category ? quiz.category.name : "No category"}
      </Typography>

      <Divider sx={{ my: 2 }} />

      <Typography variant="h5" gutterBottom>
        Questions:
      </Typography>
      {questions.length > 0 ? (
        questions.map((question, index) => (
          <Box key={question.questionId} sx={{ mb: 2 }}>
            <Typography variant="body1">
              {index + 1}. {question.text}
            </Typography>
          </Box>
        ))
      ) : (
        <Typography>No questions found.</Typography>
      )}
    </Box>
  );
}
