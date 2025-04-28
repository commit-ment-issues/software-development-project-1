export function getCategories() {
    return fetch("http://localhost:8080/api/categories", {
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
    return fetch(`http://localhost:8080/api/categories/${id}`, {
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