import { Link } from 'react-router';
import Article from '../models/Article';
import { parse, startOfDay, differenceInCalendarDays } from 'date-fns';

const PagedArticleRow = ({ article }: { article: Article }) => {
  const { headline, dateline } = article;
  const abbreviatedDateline = dateline.split('•')[0].trim();
  const daysAgo = calculateDaysAgo(abbreviatedDateline);

  function calculateDaysAgo(abbreviatedDateline: string) {
    const datelineDate = startOfDay(
      parse(abbreviatedDateline, 'MMMM d, yyyy', new Date())
    );

    const today = startOfDay(new Date());
    const diff = differenceInCalendarDays(today, datelineDate);

    if (diff === 0) return 'Today';
    return `${diff} day${diff > 1 ? 's' : ''} ago`;
  }

  return (
    <section className="cursor-pointer">
      <Link to={`${article.id}`}>
        <div className="flex flex-col md:flex-row justify-between py-2 px-4 md:pb-3 md:pt-3 border-b border-gray-200 hover:bg-gray-50 w-full">
          <div className="md:w-1/2 truncate">{headline}</div>
          <div className="md:w-1/2 flex justify-between">
            <div className="md:w-1/2 md:text-right">{abbreviatedDateline}</div>
            <div className="md:w-1/2 md:text-right">{daysAgo}</div>
          </div>
        </div>
      </Link>
    </section>
  );
};

export default PagedArticleRow;
