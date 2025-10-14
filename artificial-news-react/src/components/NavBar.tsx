import { Link } from 'react-router';
import { useGenerateArticle } from '../context/MutationProvider';

const NavBar = () => {
  const { mutate, isPending } = useGenerateArticle();

  let classNames: string;
  if (isPending) {
    classNames = "text-gray-400 bg-none p-0 border-none text-sm font-bold"
  } else {
    classNames = "text-blue-600 hover:underline bg-none p-0 border-none cursor-pointer text-sm font-bold"
  }

  return (
    <div className="flex justify-around py-4">
      <button
        onClick={() => mutate()}
        className={classNames}
      >
        GENERATE ARTICLE
      </button>
      <Link to="articles/"><span className="text-sm font-bold text-blue-600 hover:underline">VIEW ARTICLES</span></Link>
    </div>
  );
};

export default NavBar;
