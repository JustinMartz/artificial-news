export default function AuthorPhoto({
  authorPhoto,
}: {
  authorPhoto: string | undefined
}) {
  const imageUrl = `/ArtificialNews/api/photos/${authorPhoto}`
  
  return (
    <img className="size-12 lg:size-16 rounded-full" src={imageUrl} alt="Author Photo" />
  )
}
