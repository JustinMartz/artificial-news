export default function ArticlePhoto({
  articlePhoto,
}: {
  articlePhoto: string | undefined;
}) {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const imageUrl = baseUrl + `/api/photos/${articlePhoto}`;

  return (
    <div className="flex items-start lg:h-6/7 w-full py-4 lg:py-0 lg:w-1/3">
      <img src={imageUrl} alt="Article Image" />
      <span>Giraffes running around all crazy. Press pool photo by John Smith.</span>
    </div>
  );
}
