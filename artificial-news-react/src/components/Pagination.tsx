import LinkButton from './LinkButton';

const Pagination = ({
  pageNumber,
  totalElements,
  numberOfElements,
  isFirstPage,
  isLastPage,
  changePage,
}: {
  pageNumber: number;
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

  let showing;

  switch (totalElements) {
    case 0:
      showing = <span className="font-bold">0</span>;
      break;
    case 1:
      showing = <span className="font-bold">1</span>;
      break;
    default:
      showing = (
        <>
          <span className="font-bold">
            {(pageNumber + 1) * (numberOfElements - (numberOfElements - 1))}
          </span>{' '}
          to{' '}
          <span className="font-bold">
            {(pageNumber + 1) * numberOfElements}
          </span>{' of '}
          <span className="font-bold">{totalElements}</span>
        </>
      );
  }

  return (
    <div className="flex justify-between text-sm pl-4 md:text-base md:pl-0">
      <div className="flex pb-4">
        <span className="h-fit self-end">
          Showing{' '}
          {showing}{' '}
          result
          {totalElements > 1 || totalElements === 0 ? 's' : ''}
        </span>
      </div>
      <div className="flex md:gap-x-3 gap-x-2 p-4 md:pr-0">
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
