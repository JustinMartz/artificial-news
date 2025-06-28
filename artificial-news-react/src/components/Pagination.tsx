import LinkButton from "./LinkButton";

const PaginationText = () => {
    return <p>Showing 1 to 10 of 20 results</p>
}

const Pagination = () => {
    return <div className="flex justify-between md:pb-3 md:pt-3">
        <PaginationText />
        <div className="flex gap-x-3">
        <LinkButton buttonText="Previous" />
        <LinkButton buttonText="Next" />
        </div>

    </div>
}

export default Pagination;