import { Link } from 'react-router';
import { Article } from '../models/article';
import { differenceInCalendarDays } from 'date-fns';

const PagedArticleRow = ({ article }: { article: Article }) => {
  const { headline, dateline } = article;
  const abbreviatedDateline = dateline.split('•')[0].trim();
  const daysAgo = calculateDaysAgo(abbreviatedDateline);

  function calculateDaysAgo(abbreviatedDateline: string) {
    const datelineDate = new Date(
      Date.UTC(
        new Date(abbreviatedDateline).getFullYear(),
        new Date(abbreviatedDateline).getMonth(),
        new Date(abbreviatedDateline).getDate()
      )
    );

    const today = new Date(
      Date.UTC(
        new Date().getUTCFullYear(),
        new Date().getUTCMonth(),
        new Date().getUTCDate()
      )
    );

    const daysAgo = differenceInCalendarDays(today, datelineDate);

    if (daysAgo === 0) {
      return 'Today';
    }

    return `${daysAgo} day${daysAgo > 1 ? 's' : ''} ago`;
  }

  return (
    <section className="cursor-pointer">
      <Link to={`${article.id}`}>
             <div className="flex justify-between md:pb-3 md:pt-3 border-b border-gray-200 hover:bg-gray-50">
          <div>{headline}</div>
          <div>{abbreviatedDateline}</div>
          <div>{daysAgo}</div>
        </div> 
      </Link>


    </section>
  );
};

export default PagedArticleRow;
