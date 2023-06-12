import useGlobalUser from '../../context/usuario/usuario.context'
import { useNavigate } from 'react-router-dom'

export function useValidateAutentication() {
  const [, setUser] = useGlobalUser()
  const navigate = useNavigate()

  function validateAutentication(erro) {
    if (erro === 401) {
      localStorage.removeItem('user')
      navigate('/')
      setUser(null)
    }
    if (erro === 404) {
      navigate('/learn')
    }
  }

  return { validateAutentication }
}
