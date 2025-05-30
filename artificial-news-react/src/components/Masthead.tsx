import '@fontsource/unifrakturcook/700.css';
import { Link } from 'react-router';

export default function Masthead({
  headline,
}: {
  headline: string | undefined;
}) {
  return (
    <header className="mt-2 mb-2 md:mt-0 md:mb-0">
      <Link to="/">
        <h1
          style={{ fontFamily: 'UnifrakturCook, cursive' }}
          className="text-5xl"
        >
          {headline}
        </h1>
      </Link>
    </header>
  );
}
