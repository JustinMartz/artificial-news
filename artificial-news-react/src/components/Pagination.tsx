import LinkButton from './LinkButton';

const Pagination = ({
  pageSize,
  totalElements,
  numberOfElements,
  previousActive,
  nextActive,
}: {
  pageSize: number;
  totalElements: number;
  numberOfElements: number;
  previousActive: boolean;
  nextActive: boolean;
}) => {
  return (
    <div className="flex justify-between md:pb-3 md:pt-3">
      <p>Showing {numberOfElements} to {pageSize} of {totalElements}</p>
      <div className="flex gap-x-3">
        <LinkButton buttonText="Previous" />
        <LinkButton buttonText="Next" />
      </div>
    </div>
  );
};

export default Pagination;
