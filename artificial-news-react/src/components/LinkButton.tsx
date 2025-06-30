const LinkButton = ({
  buttonText,
  active,
  handleClick,
}: {
  buttonText: string;
  active: boolean;
  handleClick: () => void;
}) => {
  return (
    <button
    onClick={handleClick}
      className={
        active
          ? 'border border-gray-200 bg-green-100 py-1 px-2 rounded-sm cursor-pointer'
          : 'border border-gray-200 py-1 px-2 rounded-sm'
      }
    >
      {buttonText}
    </button>
  );
};

export default LinkButton;
