const baseurl = import.meta.env.VITE_BACKEND_URL
// https://software-development-project-1-git-quizzer.2.rahtiapp.fi/api/

export function getCategories() {
    return fetch(`${baseurl}/categories`, {
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
  
  export function getCategoryById(id) {
    return fetch(`${baseurl}/categories/${id}`, {
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

  export function getQuizzesByCategoryId(id) {
    return fetch(`${baseurl}/quizzes/categories/${id}`, {
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
