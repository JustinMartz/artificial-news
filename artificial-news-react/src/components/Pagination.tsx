import LinkButton from './LinkButton';

const Pagination = ({
  pageNumber,
  pageSize,
  totalElements,
  numberOfElements,
  isFirstPage,
  isLastPage,
  changePage,
}: {
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  numberOfElements: number;
  isFirstPage: boolean;
  isLastPage: boolean;
  changePage: React.Dispatch<React.SetStateAction<number>>;
}) => {
  const onClick = (direction: 'previous' | 'next') => {
    if (direction === 'next' && isLastPage == false) {
      changePage(pageNumber + 1);
    }

    if (direction === 'previous' && isFirstPage === false) {
      changePage(pageNumber - 1);
    }
  };

  return (
    <div className="flex justify-between md:pb-3 md:pt-3">
      <p>
        Showing{' '}
        <span className="font-bold">
          {(pageNumber + 1) * (numberOfElements - (numberOfElements - 1))}
        </span>{' '}
        to <span className="font-bold">{(pageNumber + 1) * numberOfElements}</span> of{' '}
        <span className="font-bold">{totalElements}</span> result
        {totalElements > 1 ? 's' : ''}
      </p>
      <div className="flex gap-x-3">
        <LinkButton
          buttonText="Previous"
          active={!isFirstPage}
          handleClick={() => onClick('previous')}
        />
        <LinkButton
          buttonText="Next"
          active={!isLastPage}
          handleClick={() => onClick('next')}
        />
      </div>
    </div>
  );
};

export default Pagination;
