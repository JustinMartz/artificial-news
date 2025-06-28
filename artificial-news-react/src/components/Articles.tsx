import PagedArticleRow from "./PagedArticleRow";

export default function Articles() {
  return (
    <main className="bg-white flex flex-col flex-grow md:flex-grow-0 lg:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md items-center justify-center">
          <PagedArticleRow />
          <PagedArticleRow />
          <PagedArticleRow />
    </main>
  );
}
