import ArticlePhoto from "./ArticlePhoto";

export default interface Article {
  id: string;
  dateline: string;
  creator: {
    articlesLeft: number;
  };
  headline: string;
  author: string;
  authorPhoto: string;
  articleBody: string;
  articlePhoto: ArticlePhoto; 
}
