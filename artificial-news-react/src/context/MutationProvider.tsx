import { createContext, PropsWithChildren, useContext } from 'react';
import { useCreateArticle } from '../services/articleService';
import { UseMutationResult } from '@tanstack/react-query';
import Article from '../models/Article';

const MutationContext = createContext<UseMutationResult<
  Article,
  Error,
  void,
  unknown
> | null>(null);

export const MutationProvider = ({ children }: PropsWithChildren) => {
  const mutation = useCreateArticle();

  return (
    <MutationContext.Provider value={mutation}>
      {children}
    </MutationContext.Provider>
  );
};

export const useGenerateArticle = () => {
  const context = useContext(MutationContext);
  if (!context) {
    throw new Error('useGenerateArticle must be used with a MutationProvider');
  }

  return context;
};
