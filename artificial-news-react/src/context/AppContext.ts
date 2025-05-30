import { createContext } from "react";

export interface AppContextType {
  isGenerating: boolean;
  setIsGenerating: React.Dispatch<React.SetStateAction<boolean>>;
};

export const AppContext = createContext<AppContextType | null>(null);