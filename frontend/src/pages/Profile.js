import React, { useState } from 'react';
import axios from 'axios';
import './Profile.css';

function Profile() {
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleChangePassword = async (e) => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      setMessage("As senhas não coincidem!");
      return;
    }

    try {
      await axios.post('http://localhost:8080/account/change-password', {
        newPassword
      }, { withCredentials: true });

      setMessage("Senha alterada com sucesso!");
      setOldPassword('');
      setNewPassword('');
      setConfirmPassword('');
    } catch (err) {
      console.error("Erro:", err);
    }
  };

  return (
    <div className="vaporwave-container">
      <div className="vaporwave-header">
        <h1>Fixable</h1>
        <p>Seu espaço digital personalizado</p>
      </div>

      <div className="profile-box">
        <h2>Perfil</h2>
        <img src="/profile_picture.jpg" alt="Avatar" className="avatar" />
        <p className="username">usuário_logado</p>

        <form className="password-form" onSubmit={handleChangePassword}>
          <h3>Alterar Senha</h3>
          <input
            type="password"
            placeholder="Senha atual"
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Nova senha"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Confirmar nova senha"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          <button type="submit">Atualizar Senha</button>
        </form>

        {message && <p className="message">{message}</p>}
      </div>

      <footer>
        <div className="marquee-footer">
          <div className="marquee-content">
            Fixable - Conecte com seus amigos!
          </div>
        </div>
      </footer>
    </div>
  );
}

export default Profile;
