import { useGenerateArticle } from '../context/MutationProvider';
import LinkButton from './LinkButton';

const NavBar = () => {
const { mutate, isPending } = useGenerateArticle();
  
  return (
    <div className="flex justify-around py-4">
      <LinkButton
        active={!isPending}
        handleClick={mutate}
        buttonText="Generate article"
        small
      />
      <LinkButton active={true} buttonText="View articles" to="articles/" small />
    </div>
  );
};

export default NavBar;
