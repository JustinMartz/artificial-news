import { Article } from "./article";

export interface PagedArticle {
    content: Array<Article>,
    pageable: null,
    totalPages: number,
    totalElements: number,
    last: boolean,
    size: number,
    number: number,
    sort: null,
    numberOfElements: number,
    first: boolean,
    empty: boolean
}