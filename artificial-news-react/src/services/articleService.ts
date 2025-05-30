import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import { Article } from '../models/article'
import { useNavigate } from 'react-router'
import { formatArticle } from '../lib/utils'

export function useCreateArticle() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: createArticle,
    onSuccess: (data) => {
      let articleId = data.id
      data = formatArticle(data);
      localStorage.setItem(`${articleId}`, JSON.stringify(data))
      queryClient.setQueryData(['article', articleId], data)
      navigate(`/articles/${data.id}`)
    },
  })
}

async function createArticle(): Promise<Article> {
  const response = await fetch('/ArtificialNews/api/articles', { method: 'POST' })
  if (!response.ok) {
    throw new Error('Network response was not ok')
  }
  
  return response.json();
}

export function useFetchArticleById(
  cachedArticle: Article | undefined,
  articleId: string
) {
  const queryClient = useQueryClient();
  
  return useQuery<Article | undefined, Error>({
    queryKey: ['article', articleId],
    queryFn: () => fetchArticleById(articleId!),
    staleTime: 1000 * 60 * 5,
    initialData: () => {
      const cachedData = queryClient.getQueryData<Article>([
        'article',
        articleId,
      ])

      if (cachedData) {
        return cachedData
      }

      const storedArticle = localStorage.getItem(`${articleId}`)
      if (storedArticle) {
        const parsedArticle = JSON.parse(storedArticle) as Article
        queryClient.setQueryData(['article', articleId], parsedArticle)

        return parsedArticle
      }

      return undefined
    },
    enabled: !cachedArticle,
  })
}

async function fetchArticleById(articleId: string): Promise<Article | undefined> {
  const response = await fetch('/ArtificialNews/api/articles/' + articleId, { method: 'GET' })
  if (!response.ok) {
    return undefined; 
  }
  
  const article: Article = await response.json();
  return formatArticle(article);
}

