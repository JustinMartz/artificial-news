export default function ArticlePhoto({
  articlePhoto,
}: {
  articlePhoto: string | undefined;
}) {
  const imageUrl = `/ArtificialNews/api/photos/${articlePhoto}`;

  return (
    <img src={imageUrl} alt="Article Image" />
  );
}
