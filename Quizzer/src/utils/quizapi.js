export function getQuizzes() {
    return fetch("http://localhost:8080/api/quizzes")
      .then(response => {
        if(!response.ok)
          throw new Error("Error in fetch: " + response.statusText);
        return response.json();
        });
    }