export default function ArticlePhoto({
  articlePhoto,
}: {
  articlePhoto: string | undefined;
}) {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const imageUrl = baseUrl + `/api/photos/${articlePhoto}`;

  return <img src={imageUrl} alt="Article Image" />;
}
