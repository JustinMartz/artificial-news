import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import Article from '../models/Article';
import { useNavigate } from 'react-router';
import { formatArticle } from '../lib/utils';
import PagedArticle from '../models/PagedArticle';

export function useCreateArticle() {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: createArticle,
    onSuccess: (data) => {
      let articleId = data.id;
      localStorage.setItem(`${articleId}`, JSON.stringify(data));
      queryClient.setQueryData(['article', articleId], data);
      navigate(`/articles/${data.id}`);
    },
  });
}

async function createArticle(): Promise<Article> {
  await new Promise((resolve) => setTimeout(resolve, 8000));
  const fakeResponse: Article = {
    id: crypto.randomUUID(),
    dateline: 'Friday, Feb. 7 | 9:34 p.m. MST',
    creator: { articlesLeft: 2 },
    headline: 'Buzzing Excitement at Beekeeping Competition in Minneapolis',
    author: 'Aisha Patel',
    authorPhoto: 'Aisha-Patel-1728253853732.png',
    articleBody: `In a sweet celebration of all things buzzing, bee enthusiasts from across the country gathered in Minneapolis for the annual Beekeeping Competition. The event, held at the Minneapolis Convention Center, showcased the best honey producers and hive designers in the industry. Attendees were abuzz with excitement as they browsed through the various exhibits and watched live demonstrations on beekeeping techniques.\n\nAmong the participants was veteran beekeeper, John Smith, who shared his passion for these pollinators, stating, "Beekeeping is not just a hobby, it's a way of life. These little creatures play a crucial role in our ecosystem, and it's an honor to care for them." Smith's dedication was evident in his award-winning honey, which impressed judges with its perfect balance of flavor and texture.\n\nThe competition not only highlighted the skill and craftsmanship of beekeepers but also aimed to raise awareness about the importance of bees in maintaining a healthy environment. With bee populations facing threats from pesticides and habitat loss, events like these serve as a reminder of the need to protect and preserve these vital insects. As the day came to a close, participants and spectators left with a newfound appreciation for the world of beekeeping and a deeper understanding of the delicate balance between humans and nature.`,

    articlePhoto: {
      caption:
        'Squirrels-Serenade-in-the-Mile-High-City-An-Opera-Unlike-Any-Other-1748281501907.png',
      filename: 'foo.png',
      photographer: 'Jane Doe',
    },
  };

  return formatArticle(fakeResponse);
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
      ]);

      if (cachedData) {
        return cachedData;
      }

      const storedArticle = localStorage.getItem(`${articleId}`);
      if (storedArticle) {
        const parsedArticle = JSON.parse(storedArticle) as Article;
        queryClient.setQueryData(['article', articleId], parsedArticle);

        return parsedArticle;
      }

      return undefined;
    },
    enabled: !cachedArticle,
  });
}

async function fetchArticleById(articleId: string): Promise<Article> {
  await new Promise((resolve) => setTimeout(resolve, 20000));
  const response = await fetch(
    'http://localhost:8080/api/articles/' + articleId,
    { method: 'GET' }
  );

  const dbArticle: Article = await response.json();
  const formattedArticle: Article = formatArticle(dbArticle);
  return Promise.resolve(formattedArticle);
}

export function useFetchPagedArticles(pageNumber: number, pageSize: number) {
  return useQuery<PagedArticle | undefined, Error>({
    queryKey: ['pagedArticles', pageNumber],
    queryFn: () => fetchPagedArticles(pageNumber, pageSize),
  });
}

async function fetchPagedArticles(
  pageNumber: number,
  pageSize: number
): Promise<PagedArticle | undefined> {
  await new Promise((resolve) => setTimeout(resolve, 20000));
  const response = await fetch(
    'http://localhost:8080/api/articles?page=' +
      pageNumber +
      '&size=' +
      pageSize,
    { method: 'GET' }
  );
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const pagedArticle: PagedArticle = await response.json();
  return pagedArticle;
}
