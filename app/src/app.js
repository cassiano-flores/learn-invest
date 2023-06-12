import React from 'react'
import './app.css'
import { RouterProvider } from 'react-router-dom'
import { router } from './router'
import { GlobalUserProvider } from './context/usuario/usuario.context'

function App() {
  return (
    <main className="main">
      <GlobalUserProvider>
        <RouterProvider router={router} />
      </GlobalUserProvider>
    </main>
  )
}

export default App
