import GenerateArticleButton from './GenerateArticleButton';

export default function ArticleLoading() {
  return (
    <>
      <article className="w-11/12 h-[75dvh] lg:w-4/5 lg:h-2/3 bg-white rounded-md shadow-md p-4 flex flex-col justify-center items-center">
        <h2 className="text-2xl">Page not found.</h2>
      </article>
      <GenerateArticleButton invisible />
    </>
  );
}
