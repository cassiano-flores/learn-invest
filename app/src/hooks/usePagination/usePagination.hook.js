import { useState } from 'react'

export function usePagination() {
  const PAGE_UNIT = 1
  const INITIAL_PAGE = 0

  const [page, setPage] = useState(INITIAL_PAGE)

  function goToNextPage() {
    setPage(page => page + PAGE_UNIT)
  }

  function goToPreviousPage() {
    setPage(page =>
      page - PAGE_UNIT < INITIAL_PAGE ? INITIAL_PAGE : page - PAGE_UNIT
    )
  }

  return { page, goToNextPage, goToPreviousPage }
}
