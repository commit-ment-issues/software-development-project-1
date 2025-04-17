import { useState, useEffect } from "react";
import { AgGridReact } from 'ag-grid-react';
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community'; 

import { getQuizzes } from "../utils/quizapi";

ModuleRegistry.registerModules([AllCommunityModule])

function QuizList(){

    const [quizzes, setQuizzes] = useState([]);
    const [colDefs, setColDefs] = useState([
        { field: "name", headerName: "Title",},
        { field: "description", headerName: "Description" },
        { field: "courseCode", headerName: "Course Code" },
    ]);


    useEffect(() => {
        handleFetch();
    }, []);

    const handleFetch = () => {
        getQuizzes().then(data => setQuizzes(data))
        .catch(err => console.log(err))
    }

    return ( 
        <div className="ag-theme-material" style={{ width: 600, height: 400 }}>
            <AgGridReact
                rowData={quizzes}
                columnDefs={colDefs}
            />
        </div>
    )
}

export default QuizList;