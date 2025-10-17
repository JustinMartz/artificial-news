import ArticlePhoto from '../../models/ArticlePhoto';

const ArticlePhotoAndCaption = ({
  articlePhoto,
}: {
  articlePhoto: ArticlePhoto | undefined;
}) => {
  const baseUrl = import.meta.env.VITE_BASE_URL;
  const imageUrl = baseUrl + `/api/photos/${articlePhoto?.filename}`;

  return (
    <div className="flex flex-col items-start md:h-full w-full py-4 lg:py-0 lg:w-1/3">
      <img src={imageUrl} alt={articlePhoto?.caption} />
      <span className="text-sm text-gray-400 mt-4">{articlePhoto?.caption}</span>
      <span className="text-xs text-gray-400 mt-4">
        {articlePhoto?.photographer}/The Artificial News
      </span>
    </div>
  );
};

export default ArticlePhotoAndCaption;
