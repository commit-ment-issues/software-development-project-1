import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getQuizById, getQuestionsByQuizId } from "../utils/quizapi";

function QuestionList() {
  const { quizId } = useParams();
  const [quiz, setQuiz] = useState(null);
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
      .then(questions => setQuestions(questions))
      .catch(err => setError(err.message));
  }, [quizId]);

  if (error) return <div>Error: {error}</div>;
  if (!quiz) return <div>Loading...</div>;

  return (
    <div>
      <h2>{quiz.name}</h2>
      <p>{quiz.description}</p>
      <h4>Questions:</h4>
      <ul>
        {questions.length > 0 ? (
          questions.map((q, index) => (
            <li key={q.id || q.questionId}>
              {index + 1}. {q.questionText || q.text}
            </li>
          ))
        ) : (
          <li>No questions found.</li>
        )}
      </ul>
    </div>
  );
}

export default QuestionList;
