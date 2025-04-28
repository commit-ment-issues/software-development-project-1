import React, { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { HashRouter, Routes, Route } from 'react-router-dom'
import App from './App.jsx'
import CategoryList from './Components/CategoryList.jsx'
import CategoryPage from './Components/CategoryPage.jsx'

createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <HashRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="categories" element={<CategoryList />} />
        <Route path="categories/:categoryid" element={<CategoryPage />} />
        
      </Routes>
    </HashRouter>

  </React.StrictMode>
)
