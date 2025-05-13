const baseurl = import.meta.env.VITE_BACKEND_URL

export function getQuizzes() {
  return fetch(`${baseurl}/quizzes`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch: " + response.statusText);
      return response.json();
    });
}

export function getPublishedQuizzes() {
  return fetch(`${baseurl}/quizzes/published`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch: " + response.statusText);
      return response.json();
    });
}

export function getQuizById(id) {
  return fetch(`${baseurl}/quizzes/${id}`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch: " + response.statusText);
      return response.json();
    });
}

export function getQuestionsByQuizId(id) {
  return fetch(`${baseurl}/quiz/${id}/questions`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok)
        throw new Error("Error in fetch: " + response.statusText);
      return response.json();
    });
}

export function updateResults(id, answer) {
  return fetch(`${baseurl}/question/updateanswer/${id}`,{
    method: 'PUT',
    headers: { 
      'Accept': 'application/json',
      'Content-Type': 'application/json' },
    body: JSON.stringify(answer)
  })
  .then(response => {
      if(!response.ok) {
        throw new Error("Error in updating results")
      }
      return response.json();
    })
}
