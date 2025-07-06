import { useParams } from 'react-router';
import { AppContext } from '../context/AppContext';
import { useContext } from 'react';
import { useQueryClient } from '@tanstack/react-query';
import { Article } from '../models/Article';
import { useFetchArticleById } from '../services/articleService';
// import { useFetchArticleById } from '../services/mockGetArticleService';
import ArticleHeadline from './ArticleHeadline';
import ArticleDateline from './ArticleDateline';
import ArticlePhoto from './ArticlePhoto';
import ArticleBody from './ArticleBody';
import ArticleNotFound from './ArticleNotFound';
import ArticleLoading from './ArticleLoading';

export default function RenderedArticle() {
  const { articleId } = useParams();
  const queryClient = useQueryClient();
  const context = useContext(AppContext);

  if (!context) {
    throw new Error(
      'GeneratedArticle.tsx must be used within AppContext.Provider'
    );
  }

  const cachedArticle = queryClient.getQueryData<Article>([
    'article',
    articleId,
  ]);
  const articleQuery = useFetchArticleById(cachedArticle, articleId ?? '');

  if (articleQuery.isLoading) {
    return <ArticleLoading />;
  }

  if (articleQuery.isError) {
    return <ArticleNotFound />;
  }

  return (
    <main className="bg-white flex flex-grow md:flex-grow-0 lg:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
      <title>{articleQuery.data?.headline}</title>

      <article className="flex flex-col lg:justify-between w-screen h-fit lg:h-[75dvh] px-4 py-2 md:mb-4 md:mt-4 lg:mb-0 lg:mt-0">
        <ArticleHeadline
          headline={articleQuery.data?.headline}
        ></ArticleHeadline>
        <ArticleDateline dateline={articleQuery.data?.dateline} />
        <div className="flex flex-col lg:flex-row w-full lg:h-6/7">
          <div className="flex items-start lg:h-6/7 w-full py-4 lg:py-0 lg:w-1/3">
            <ArticlePhoto articlePhoto={articleQuery.data?.articlePhoto} />
          </div>

          <ArticleBody
            articleBody={articleQuery.data?.articleBody}
            author={articleQuery.data?.author}
            authorPhoto={articleQuery.data?.authorPhoto}
          />
        </div>
      </article>
    </main>
  );
}
