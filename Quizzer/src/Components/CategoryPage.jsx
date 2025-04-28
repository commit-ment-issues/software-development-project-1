import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getCategoryById } from "../utils/categoryapi";

function CategoryPage() {
  const { categoryid } = useParams();
  const [category, setCategory] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    getCategoryById(categoryid)
      .then(data => setCategory(data))
      .catch(err => setError(err.message));
  }, [categoryid]);

  if (error) return <div>Error: {error}</div>;
  if (!category) return <div>Loading...</div>;

  return (
    <div>
      <h2>{category.name}</h2>
      <p>{category.description}</p>
    </div>
  );
}

export default CategoryPage;