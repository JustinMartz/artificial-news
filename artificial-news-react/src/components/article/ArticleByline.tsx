import AuthorPhoto from './AuthorPhoto';

interface ArticleBylineProps {
  author?: string;
  authorPhoto?: string;
}

export default function ArticleByline({
  author,
  authorPhoto,
}: ArticleBylineProps) {
  return (
    <div className="flex items-center w-full mb-2 lg:mb-0 lg:w-fit h-fit">
      <AuthorPhoto authorPhoto={authorPhoto} />
      <h2 className="md:text-lg font-bold inline ml-4">By {author}</h2>
    </div>
  );
}
