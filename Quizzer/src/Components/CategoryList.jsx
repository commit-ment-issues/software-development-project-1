import { useState, useEffect } from "react";
import { AgGridReact } from 'ag-grid-react';
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';
import { Link } from "react-router-dom";

import { getCategories } from "../utils/categoryapi";

ModuleRegistry.registerModules([AllCommunityModule])

function CategoryList() {

    const [categories, setCategories] = useState([]);
    const [colDefs, _setColDefs] = useState([
        { field: "name", headerName: "Title", cellRenderer: (params) => (
            <Link to={`/categories/${params.data.categoryid}`} style={{ color: "#57B9FF" }}>
              {params.value}
            </Link>
          )},
        { field: "description", headerName: "Description", flex: 2 }
    ]);


    useEffect(() => {
        handleFetch();
    }, []);

    const handleFetch = () => {
        getCategories().then(data => setCategories(data))
            .catch(err => console.log(err))
    }

    return (
        <div className="ag-theme-material" style={{ width: "100%", height: 400 }}>
            <h1>Categories</h1>
            <AgGridReact
                rowData={categories}
                columnDefs={colDefs}
            />
        </div>
    )
}

export default CategoryList;