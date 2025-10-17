import Article from "../models/Article";

export function formatArticle(article: Article): Article {
    const lines = article.articleBody.split('\n').filter(item => item.length !== 0);
  
    let formattedArticleBody = '';
  
    // Alternate between opening <p> tags and closing </p> tags
    for (let i = 0; i < lines.length; i++) {
        if (i === lines.length - 1) {
            formattedArticleBody += `<p>${lines[i]}</p>`;
        } else {
            formattedArticleBody += `<p>${lines[i]}</p><br />`;
        }

    }

    return { ...article, articleBody: formattedArticleBody };
  }