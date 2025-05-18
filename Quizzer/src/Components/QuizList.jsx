import { useState, useEffect } from "react";
import { AgGridReact } from 'ag-grid-react';
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community'; 
import { Link } from "react-router-dom";

import { getPublishedQuizzes } from "../utils/quizapi";

ModuleRegistry.registerModules([AllCommunityModule])

function QuizList(){

    const [quizzes, setQuizzes] = useState([]);
    const [colDefs, _setColDefs] = useState([
        { field: "name", headerName: "Title", flex: 1, sortable: true, filter: true,
            cellRenderer: (params) => (
                <Link to={`quiz/${params.data.quizId}/questions`} style={{ color: "#57B9FF" }}>
                    {params.value}
                </Link>
          )},
        { field: "description", headerName: "Description", flex: 1, sortable: true, filter: true },
        { field: "courseCode", headerName: "Course", flex: 1, sortable: true, filter: true },
        { field: "category", headerName: "Category", flex: 1, sortable: true, filter: true,
            cellRenderer: (params) => params.value?.name },
        { field: "creationDate", headerName: "Added on", flex: 1, sortable: true, filter: true },
        { cellRenderer: (params) => (
            <Link to={`quiz/${params.data.quizId}/results`} style={{ color: "#57B9FF" }}>
                See results
            </Link>
        )},
        { cellRenderer: (params) => (
            <Link to={`quiz/${params.data.quizId}/reviewlist`} style={{ color: "#57B9FF" }}>
                See reviews
            </Link>
        )}
    ]);


    useEffect(() => {
        handleFetch();
    }, []);

    const handleFetch = () => {
        getPublishedQuizzes()
            .then(data => setQuizzes(data))
            .catch(err => console.log(err))
    }

    return ( 
        <div className="ag-theme-material">
            <h1>Quizzes</h1>
            <AgGridReact
                rowData={quizzes}
                columnDefs={colDefs}
                pagination={true}
                paginationAutoPageSize={true}
                suppressCellFocus={true}
                domLayout='autoHeight'
            />
        </div>
    )
}

export default QuizList;