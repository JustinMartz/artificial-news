import ArticleByline from './ArticleByline'

interface ArticleBodyProps {
  articleBody: string | undefined
  author?: string
  authorPhoto?: string
}

export default function ArticleBody({
  articleBody,
  author,
  authorPhoto,
}: ArticleBodyProps) {
  return (
    <div className="flex-1 lg:ml-4 overflow-scroll">
      <div className="float-left h-min w-full lg:w-fit lg:mr-4">
        <ArticleByline author={author} authorPhoto={authorPhoto} />
      </div>

      <div
        className="font-tinos font-normal h-full"
        dangerouslySetInnerHTML={{ __html: articleBody || '' }}
      />
    </div>
  )
}
