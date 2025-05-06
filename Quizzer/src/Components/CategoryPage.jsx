import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getCategoryById } from "../utils/categoryapi";
import Typography from '@mui/material/Typography';
import { AgGridReact } from 'ag-grid-react';
import { getQuizzesByCategoryId } from "../utils/categoryapi";
import { Link } from 'react-router-dom';

function CategoryPage() {
  const { categoryid } = useParams();
  const [category, setCategory] = useState(null);
  const [quizzes, setQuizzes] = useState([]);
  const [error, setError] = useState(null);

  //Nimen linkki on vain placeholder. Tehty ennenkuin yksittäistä quizia on voitu tarkastella.
  //See results linkki on vain placeholder. Tehty ennenkuin results osiota on tehty.
  const [colDefs, _setColDefs] = useState([
    {
      field: "name", headerName: "Title", cellRenderer: (params) => (
        <Link to={`/quizzes/${params.data.quizId}`} style={{ color: "#57B9FF" }}>
          {params.value}
        </Link>
      )
    },
    { field: "description", headerName: "Description", flex: 2 },
    { field: "courseCode", headerName: "Course" },
    { field: "creationDate", headerName: "Added on" },
    {
      headerName: "",
      cellRenderer: (params) => (
        <Link
          to={`/results/${params.data.quizId}`}
          style={{ color: "#57B9FF" }}>
          See Results
        </Link>
      ),
      maxWidth: 130
    }
  ]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [categoryData, quizzesData] = await Promise.all([
          getCategoryById(categoryid),
          getQuizzesByCategoryId(categoryid)
        ]);
        setCategory(categoryData);
        setQuizzes(quizzesData);
      } catch (err) {
        setError(err.message);
      }
    };

    fetchData();
  }, [categoryid]);

  if (error) return <div>Error: {error}</div>;
  if (!category) return <div>Loading...</div>;

  return (
    <div>
      <Typography variant="h4">{category.name}</Typography>
      <p>{category.description}</p>
      <AgGridReact
        rowData={quizzes}
        columnDefs={colDefs}
        domLayout='autoHeight'
      />
    </div>
  );
}

export default CategoryPage;