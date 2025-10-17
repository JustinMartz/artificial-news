export default function ArticlePhoto({
  articlePhoto,
}: {
  articlePhoto: string | undefined;
}) {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const imageUrl = baseUrl + `/api/photos/${articlePhoto}`;

  return (
    <div className="flex flex-col items-start  md:h-full w-full py-4 lg:py-0 lg:w-1/3">
      <img src={imageUrl} alt="Article Image" />
        <span className="text-sm text-gray-500 mt-4">
          Giraffes running around Seattle all crazy in a marathon.
        </span>
        <span className="text-xs text-gray-500 mt-4">
          John Smith/The Artificial News
        </span>
    </div>
  );
}
