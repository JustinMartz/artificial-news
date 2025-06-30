import { Link } from 'react-router';
import { Article } from '../models/Article';
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
        <div className="flex flex-col md:flex-row justify-between py-2 px-4 md:pb-3 md:pt-3 border-b border-gray-200 hover:bg-gray-50 w-full">
          <div className="md:w-1/2">{headline}</div>
          <div className="md:w-1/2 flex justify-between">
            <div className="md:w-1/2">{abbreviatedDateline}</div>
            <div className="md:w-1/2">{daysAgo}</div>
          </div>
        </div>
      </Link>
    </section>
  );
};

export default PagedArticleRow;
