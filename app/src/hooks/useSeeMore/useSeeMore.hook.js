import { useState } from 'react'

export function useSeeMore() {
  const PAGE_SIZE = 10

  const [pageSize, setPageSize] = useState(PAGE_SIZE)

  function seeMore() {
    setPageSize(pageSize => pageSize + PAGE_SIZE)
  }

  return { pageSize, seeMore }
}
