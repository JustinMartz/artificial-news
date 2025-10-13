import { useCreateArticle } from '../services/articleService';
import LinkButton from './LinkButton';

const NavBar = () => {
  const mutation = useCreateArticle();

  function handleClick() {
    mutation.mutate();
  }
  return (
    <div className="flex justify-around py-4">
      <LinkButton
        active={true}
        handleClick={handleClick}
        buttonText="Generate article"
        small
      />
      <LinkButton active={true} buttonText="View articles" to="articles/" small />
    </div>
  );
};

export default NavBar;
