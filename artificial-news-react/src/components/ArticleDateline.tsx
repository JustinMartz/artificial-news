export default function ArticleDateline({dateline}: {dateline: string | undefined}) {
  return (
    <section className="w-full flex lg:justify-center mt-2 lg:mt-0">
      <h4 className="text-gray-400">{dateline}</h4>
    </section>
  );
}
