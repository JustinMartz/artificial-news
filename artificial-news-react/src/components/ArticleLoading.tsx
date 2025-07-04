export default function ArticleLoading() {
  return (
    <main className="bg-white flex flex-grow md:flex-grow-0 md:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
      <article className="flex flex-col lg:justify-between w-screen h-fit lg:h-[75dvh] p-4 md:py-2 md:mb-4 md:mt-4 lg:mb-0 lg:mt-0">
        <section className="w-full flex gap-3 flex-col md:flex-row justify-left md:justify-center md:mt-2 animate-pulse">
          <div className="w-1/2 md:w-1/2 size-6 md:size-6 rounded-xs bg-gray-200"></div>
          <div className="w-4/5 md:hidden size-6 rounded-xs bg-gray-200"></div>
          <div className="w-2/3 md:hidden size-6 rounded-xs bg-gray-200"></div>
        </section>
        <section className="w-full flex lg:justify-center mt-4 mb-1 md:mt-0 animate-pulse">
          <div className="w-1/4 md:w-1/6 size-4 md:size-4 rounded-xs bg-gray-200"></div>
        </section>
        <div className="flex flex-col lg:flex-row w-full lg:h-6/7">
          <div className="flex items-start  lg:h-6/7 w-full py-4 lg:py-0 lg:w-1/3">
            <div className="w-full md:w-full size-82 md:size-98 rounded-xs bg-gray-200"></div>
          </div>
        </div>
      </article>
    </main>
  );
}
