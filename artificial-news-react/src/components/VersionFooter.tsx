import { APP_VERSION } from '../variables';

const VersionFooter = () => {
  return <footer className="mb-2 mt-2 md:mb-4 md:mt-0 text-sm">{APP_VERSION}</footer>;
};

export default VersionFooter;
