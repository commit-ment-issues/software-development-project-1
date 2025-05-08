export async function getReviews() {
  return fetch('http://localhost:8080/api/reviews', {
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

export async function addReview() {
  return fetch('http://localhost:8080/api/quizzes/${quizId}/reviews', {
    method: "POST",
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

  export async function getReviewsByQuizId(id) {
    const response = await fetch(`http://localhost:8080/api/reviews/quiz/${id}`);
    if (!response.ok) {
      throw new Error("Error in fetch: " + response.statusText);
    }
    return await response.json();
  }

