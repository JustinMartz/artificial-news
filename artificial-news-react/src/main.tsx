import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './styles/index.css'
import App from './components/App.tsx'
import { BrowserRouter, Route, Routes } from 'react-router'
import Welcome from './components/Welcome.tsx'
import Articles from './components/Articles.tsx'
import RenderedArticle from './components/RenderedArticle.tsx'
import PageNotFound from './components/PageNotFound.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter basename="/ArtificialNews">
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<Welcome />} />
          <Route path="articles" element={<Articles />} />
          <Route path="articles/:articleId" element={<RenderedArticle />} />
          <Route path="*" element={<PageNotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
)
