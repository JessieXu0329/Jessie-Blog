// SHA-256("Jessiexu123456") — 源码只存哈希，不存明文
const PASSWORD_HASH = 'd394da102352df32e01b2bb117a5e656d625d0e394e259ba6e34ef7024b73084'
const SESSION_KEY   = 'jessie_admin_authed'

async function sha256(text) {
  const buf  = await crypto.subtle.digest('SHA-256', new TextEncoder().encode(text))
  return Array.from(new Uint8Array(buf)).map(b => b.toString(16).padStart(2, '0')).join('')
}

export function useAdminAuth() {
  function isAuthed() {
    return sessionStorage.getItem(SESSION_KEY) === '1'
  }

  async function login(password) {
    const hash = await sha256(password)
    if (hash === PASSWORD_HASH) {
      sessionStorage.setItem(SESSION_KEY, '1')
      return true
    }
    return false
  }

  function logout() {
    sessionStorage.removeItem(SESSION_KEY)
  }

  return { isAuthed, login, logout }
}
