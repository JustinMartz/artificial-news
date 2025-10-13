import { Link } from 'react-router';

const LinkButton = ({
  buttonText,
  active,
  handleClick,
  to,
}: {
  buttonText: string;
  active: boolean;
  handleClick?: () => void;
  to?: string;
}) => {
  return (
    <>
      {to ? (
        <Link to={to}>
          <button
            className={
              active
                ? 'border border-gray-200 bg-sky-400 py-1 px-2 rounded-sm cursor-pointer'
                : 'border border-gray-200 py-1 px-2 rounded-sm bg-gray-100'
            }
          >
            {buttonText}
          </button>
        </Link>
      ) : (
        <button
          onClick={handleClick}
          className={
            active
              ? 'border border-gray-200 bg-sky-400 py-1 px-2 rounded-sm cursor-pointer'
              : 'border border-gray-200 py-1 px-2 rounded-sm bg-gray-100'
          }
        >
          {buttonText}
        </button>
      )}
    </>
  );
};

export default LinkButton;
