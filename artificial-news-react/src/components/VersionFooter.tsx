import { APP_VERSION } from '../variables';

const VersionFooter = () => {
  return (
    <footer className="mb-2 mt-2 md:mb-4 md:mt-0 text-sm">
      <a
        className="text-blue-600 hover:underline"
        href="https://github.com/JustinMartz/artificial-news/releases"
      >
        {APP_VERSION}
      </a>
    </footer>
  );
};

export default VersionFooter;
