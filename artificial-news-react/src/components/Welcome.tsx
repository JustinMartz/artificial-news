import GeneratingArticleLoader from './GeneratingArticleLoader';
import { useCreateArticle } from '../services/articleService';
import phoneImage from '../assets/iphone-tilted-738x1238.png';
import LinkButton from './LinkButton';

export default function Welcome() {
  const mutation = useCreateArticle();

  function handleClick() {
    mutation.mutate();
  }

  return (
    <>
      {mutation.isPending ? (
        <GeneratingArticleLoader />
      ) : (
        <main className="bg-white flex flex-grow md:flex-grow-0 lg:h-[75dvh] w-11/12 md:w-4/5 rounded-md shadow-md">
          <article className="flex justify-between p-2 lg:p-4">
            <section className="hidden lg:block lg:h-auto lg:w-1/3">
              <img
                className="h-full w-full object-contain"
                alt="iPhone displaying The Artificial News"
                src={phoneImage}
              />
            </section>
            <section className="flex flex-col justify-between items-start h-auto p-2 py-0 lg:ml-4 lg:py-10 lg:pr-10 lg:w-2/3">
              <p className="text-base text-justify lg:text-start lg:text-2xl">
                Maximizing shareholder value and optimizing projection modeling
                can be challenging when you don't know who to trust for reliable
                data points. When you need to drill down into operationalizing
                the vision,&nbsp;
                <em>
                  <strong>The Artificial News</strong>
                </em>
                &nbsp;has you covered. Manifest an article, share it, and see
                what other enterprise rockstars have created.
              </p>

              <section className="w-full">
                <ul className="text-base space-y-4 lg:text-xl lg:space-y-6">
                  <li className="flex items-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth={1.5}
                      stroke="currentColor"
                      className="size-6 mr-2 stroke-red-500"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M7.5 21 3 16.5m0 0L7.5 12M3 16.5h13.5m0-13.5L21 7.5m0 0L16.5 12M21 7.5H7.5"
                      />
                    </svg>
                    Leverage cross-functional synergies
                  </li>
                  <li className="flex items-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth={1.5}
                      stroke="currentColor"
                      className="size-6 mr-2 stroke-emerald-500"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z"
                      />
                    </svg>
                    Optimize strategic bandwidth
                  </li>
                  <li className="flex items-center">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth="1.5"
                      stroke="currentColor"
                      className="size-6 mr-2 stroke-violet-500"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M3.75 3v11.25A2.25 2.25 0 0 0 6 16.5h2.25M3.75 3h-1.5m1.5 0h16.5m0 0h1.5m-1.5 0v11.25A2.25 2.25 0 0 1 18 16.5h-2.25m-7.5 0h7.5m-7.5 0-1 3m8.5-3 1 3m0 0 .5 1.5m-.5-1.5h-9.5m0 0-.5 1.5M9 11.25v1.5M12 9v3.75m3-6v6"
                      />
                    </svg>
                    Seamlessly integrate key initiatives
                  </li>
                </ul>
              </section>

            <div className="flex md:gap-x-6 justify-between md:justify-start pb-2 md:pb-0 w-full">
              <LinkButton
                active={true}
                handleClick={handleClick}
                buttonText="Generate article"
              />
              <LinkButton
                active={true}
                buttonText="View articles"
                to="articles/"
              />

            </div>

            </section>
          </article>
        </main>
      )}
    </>
  );
}
