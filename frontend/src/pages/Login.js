import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/auth/login', {
        username,
        password
      }, {
        withCredentials: true
      });

      console.log('Login bem-sucedido:', response.data);
      navigate('/profile');
    } catch (err) {
      console.error('Erro no login:', err);
      alert('Login falhou');
    }
  };

  const goToRegister = () => {
    navigate('/register');
  };

  return (
    <div className="vaporwave-container">
      <div className="vaporwave-header">
        <h1>Fixable</h1>
        <p>A web como ela deveria ser.</p>
      </div>

      <div className="login-box">
        <form onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="Usuário"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          /><br />
          <input
            type="password"
            placeholder="Senha"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          /><br />
          <button type="submit">Entrar</button>
        </form>
        <button onClick={goToRegister} className="register-btn">Criar Conta</button>
      </div>

      <div className="footer">
        <p>© 2004 Fixable Network</p>
      </div>
      <footer>
        <div className="marquee-footer">
          <div className="marquee-content">
            Bem-vindo à Fixable - A sua rede social melhor que o Orkut! Personalize seu perfil e entre na onda!
          </div>
        </div>
      </footer>
    </div>
    
  );
}

export default Login;
