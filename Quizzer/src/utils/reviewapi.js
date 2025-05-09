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

export async function saveReview(id, review) {
  return fetch(`http://localhost:8080/api/quizzes/${id}/reviews`, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: JSON.stringify(review)
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

