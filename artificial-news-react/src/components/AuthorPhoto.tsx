import.meta.env.VITE_BASE_URL;

export default function AuthorPhoto({
  authorPhoto,
}: {
  authorPhoto: string | undefined;
}) {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const imageUrl = baseUrl + `/api/photos/${authorPhoto}`  

  return (
    <img
      className="size-12 lg:size-16 rounded-full"
      src={imageUrl}
      alt="Author Photo"
    />
  );
}
