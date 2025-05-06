import { useState, useEffect} from "react";
import { AgGridReact } from "ag-grid-react";
import { useParams } from "react-router-dom";

import { getQuizById, getQuestionsByQuizId } from "../utils/quizapi";
import { Typography } from "@mui/material";


function ResultsPage() {
    const quizId = useParams().quizId;
    const [quiz, setQuiz] = useState([]);

    const [questions, setQuestions] = useState([])
    const [colDefs, setColDefs] = useState([
        { field: "questionText", headerName: "Name" },
        { field: "difficulty", headerName: "Difficulty" },
        { field: "totalAnswers", headerName: "Total Answers" },
        { headerName: "Correct Answer %",
            valueGetter: (params) => {
                const totalAnswers = params.data.totalAnswers;
                const correctAnswers = params.data.correctAnswers;
                return totalAnswers > 0 ? ((correctAnswers / totalAnswers) * 100).toFixed(2) + "%" : "0%";
            }
        },
        { field: "correctAnswers", headerName: "Correct Answers" },
        { field: "wrongAnswers", headerName: "Wrong Answers" }
    ])
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
      }, [quizId])

  if (error) return <div>Error: {error}</div>;
  if (!quiz) return <div>Loading...</div>;

  return (
        <div className="ag-theme-material">
            <Typography variant="h4">Results of "{quiz.name}"</Typography>
            <AgGridReact
                rowData={questions}
                columnDefs={colDefs}
                domLayout='autoHeight'
            />
        </div>
  );
}

export default ResultsPage;