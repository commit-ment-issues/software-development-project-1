import { useState } from 'react'
import './App.css'

function App() {
  fetch("http://localhost:8080/api/quizzes").then((quizzes) =>
    console.log(quizzes)
  );
  

  return (
    <>
    </>
  )
}

export default App
