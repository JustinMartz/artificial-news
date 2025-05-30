export default function ArticleHeadline({
  headline,
}: {
  headline: string | undefined;
}) {
  return (
    <section className="w-full flex justify-center">
      <h2 className="font-tinos text-3xl font-bold">{headline}</h2>
    </section>
  );
}
