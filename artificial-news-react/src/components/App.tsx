import { Outlet } from 'react-router';
import '../styles/App.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Masthead from './Masthead';
import VersionFooter from './VersionFooter';
import { MutationProvider } from '../context/MutationProvider';
import { AppContext } from '../context/AppContext';
import { useState } from 'react';

export default function App() {
  const [isGenerating, setIsGenerating] = useState<boolean>(false);
  const queryClient = new QueryClient();

  return (
    <AppContext.Provider value={{ isGenerating, setIsGenerating }}>
      <QueryClientProvider client={queryClient}>
        <MutationProvider>
          <Masthead headline="The Artificial News" />
          <Outlet />
          <VersionFooter />
        </MutationProvider>
      </QueryClientProvider>
    </AppContext.Provider>
  );
}
