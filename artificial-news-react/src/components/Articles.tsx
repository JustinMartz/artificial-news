import PagedArticleRow from './PagedArticleRow';
import Pagination from './Pagination';

export default function Articles() {
  return (
    <main className="bg-white md:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
      <article className="flex flex-col justify-between md:w-full md:p-6 md:h-full overflow-y-auto">
        <div>
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
        </div>

        <Pagination />
      </article>
    </main>
  );
}
