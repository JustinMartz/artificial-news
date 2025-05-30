interface GenerateArticleButtonProps {
  clickHandler?: () => void
  status?: boolean,
  invisible?: boolean,
}

export default function GenerateArticleButton({
  clickHandler,
  status,
  invisible,
}: GenerateArticleButtonProps) {
  const classes = status
    ? 'mb-4 p-2 mt-6 bg-gray-400 cursor-progress'
    : `p-2 bg-green-500 text-white cursor-pointer ${invisible ? 'hidden lg:block lg:invisible' : ''}`
  return (
      <button onClick={clickHandler} type="submit" className={classes}>
        {status ? 'Generating article' : 'Generate article'}
      </button>
  )
}
