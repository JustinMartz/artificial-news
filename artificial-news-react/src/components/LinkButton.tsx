import { Link } from 'react-router';

const LINK_BUTTON_BASE: string =
  'border border-gray-200 py-1 px-2 rounded-sm';
const LINK_BUTTON_ACTIVE: string = LINK_BUTTON_BASE + ' bg-sky-400 cursor-pointer';
const LINK_BUTTON_INACTIVE: string = LINK_BUTTON_BASE + ' bg-gray-100';
const LINK_BUTTON_SMALL: string = ' text-sm';

const LinkButton = ({
  buttonText,
  active,
  handleClick,
  to,
  small = true,
}: {
  buttonText: string;
  active: boolean;
  handleClick?: () => void;
  to?: string;
  small?: boolean;
}) => {
  let linkButtonClasses: string = LINK_BUTTON_BASE;

  if (active) {
    linkButtonClasses = LINK_BUTTON_ACTIVE;
  } else {
    linkButtonClasses = LINK_BUTTON_INACTIVE;
  }

  if (small) {
    linkButtonClasses = linkButtonClasses + LINK_BUTTON_SMALL;
  }

  return (
    <>
      {to ? (
        <Link to={to}>
          <button className={linkButtonClasses}>{buttonText}</button>
        </Link>
      ) : (
        <button onClick={handleClick} className={linkButtonClasses}>
          {buttonText}
        </button>
      )}
    </>
  );
};

export default LinkButton;
