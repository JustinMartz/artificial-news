import { useState } from 'react';
import { useFetchPagedArticles } from '../services/articleService';
import PagedArticleRow from './PagedArticleRow';
import Pagination from './Pagination';
import { useGenerateArticle } from '../context/MutationProvider';
import GeneratingArticleLoader from './GeneratingArticleLoader';

export default function Articles() {
  const [pageNumber, setPageNumber] = useState<number>(0);
  const [pageSize] = useState<number>(10);
  const { isPending } = useGenerateArticle();

  const pagedArticlesResult = useFetchPagedArticles(pageNumber, pageSize);
  const { number, totalElements, numberOfElements, first, last } =
    pagedArticlesResult?.data ?? {
      size: 0,
      number: 0,
      totalElements: 0,
      numberOfElements: 0,
      first: false,
      last: false,
    };

  if (isPending) {
    return <GeneratingArticleLoader />;
  }

  return (
    <main className="bg-white flex flex-grow md:flex-grow-0 lg:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
      <article className="flex flex-col justify-between w-full md:p-6 md:h-full">
        <div className="overflow-y-auto w-full">
          {pagedArticlesResult.isLoading ? (
            <div className="px-4">
              {Array(3)
                .fill('')
                .map((_, index) => (
                  <div
                    className={`flex flex-col w-full md:flex-row justify-between py-2 md:pb-3 md:pt-3 ${
                      index === 2 ? '' : 'border-b'
                    } border-gray-200 w-full animate-pulse`}
                  >
                    <div className="h-14 my-2 md:my-0 md:h-6 md:w-full rounded-xs bg-gray-200"></div>
                  </div>
                ))}
            </div>
          ) : null}
          {pagedArticlesResult.isSuccess
            ? pagedArticlesResult.data?.content.map((article, index, array) => {
                const isLast = index === array.length - 1;

                return (
                  <PagedArticleRow
                    key={index}
                    article={article}
                    isLast={isLast}
                    isEven={index % 2 === 0}
                  />
                );
              })
            : null}
        </div>
        {pagedArticlesResult.isSuccess ? (
          <Pagination
            pageNumber={number}
            totalElements={totalElements}
            numberOfElements={numberOfElements}
            isFirstPage={first}
            isLastPage={last}
            changePage={setPageNumber}
          />
        ) : null}
      </article>
    </main>
  );
}
