const baseurl = import.meta.env.VITE_BACKEND_URL
// https://software-development-project-1-git-quizzer.2.rahtiapp.fi/api/

export async function getReviews() {
  return fetch(`${baseurl}/reviews`, {
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
  return fetch(`${baseurl}/quizzes/${id}/reviews`, {
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
    const response = await fetch(`${baseurl}/reviews/quiz/${id}`);
    if (!response.ok) {
      throw new Error("Error in fetch: " + response.statusText);
    }
    return await response.json();
  }

