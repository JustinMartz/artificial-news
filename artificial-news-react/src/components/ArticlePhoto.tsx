export default function ArticlePhoto({
  articlePhoto,
}: {
  articlePhoto: string | undefined;
}) {
  // const imageUrl = `/ArtificialNews/api/photos/${articlePhoto}`;
  const imageUrl = `http://localhost:8080/api/photos/${articlePhoto}`

  return (
    <img src={imageUrl} alt="Article Image" />
  );
}
