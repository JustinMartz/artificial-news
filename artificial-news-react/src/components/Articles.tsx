import { useFetchPagedArticles } from '../services/mockGetArticleService';
import PagedArticleRow from './PagedArticleRow';
import Pagination from './Pagination';

export default function Articles() {
  // pagination info to Pagination
  const pagedArticlesResult = useFetchPagedArticles(0, 10);
  const { size, number, totalElements, numberOfElements } = pagedArticlesResult?.data ?? {
    size: 0,
    number: 0,
    totalElements: 0,
    numberOfElements: 0
  };

  return (
    <main className="bg-white md:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
      <article className="flex flex-col justify-between md:w-full md:p-6 md:h-full overflow-y-auto">
        <div>
          {pagedArticlesResult.isSuccess
            ? pagedArticlesResult.data?.content.map((article, index) => (
                <PagedArticleRow key={index} article={article} />
              ))
            : null}
        </div>

        <Pagination
          pageSize={size}
          totalElements={totalElements}
          numberOfElements={numberOfElements}
          previousActive={false}
          nextActive={true}
        />
      </article>
    </main>
  );
}
