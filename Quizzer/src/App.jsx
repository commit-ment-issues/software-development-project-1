import { useEffect, useState } from 'react';
import { AgGridReact } from 'ag-grid-react';
import './App.css'

function App() {
  fetch("http://localhost:8080/api/quizzes").then((quizzes) =>
    console.log(quizzes)
  );
  
  const [quizzes, setQuizzes] = useState([]);

  useEffect(() => {
    handleFetch();
  }, []);

  const handleFetch = () =>{
    return fetch("http://localhost:8080/api/quizzes")
      .then(response => {
        if(!response.ok)
          throw new Error("Error in fetch: " + response.statusText);
        return response.json();
      }).then(data => setQuizzes(data))
  }

  return (
    <>
    <h1>Quizzes</h1>
    <div>
      <ul>{quizzes.map((quiz) => (<li key = {quiz.quizId}>{quiz.name}</li>))}</ul>
    </div>
    </>
  )
}

export default App
