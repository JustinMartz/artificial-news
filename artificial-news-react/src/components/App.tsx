import {  Outlet } from 'react-router';
import '../styles/App.css';
import { useState } from 'react';
import { AppContext } from '../context/AppContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Masthead from './Masthead';
import VersionFooter from './VersionFooter';

export default function App() {
  const [isGenerating, setIsGenerating] = useState<boolean>(false);
  const queryClient = new QueryClient();

  return (
    <AppContext.Provider value={{ isGenerating, setIsGenerating }}>
      <QueryClientProvider client={queryClient}>
          <Masthead headline="The Artificial News" />
          <Outlet />
          <VersionFooter />
      </QueryClientProvider>
    </AppContext.Provider>
  );
}
