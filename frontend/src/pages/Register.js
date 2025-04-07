import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css';

function Register() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/auth/register', {
                username,
                password
            });
    
            alert('Usuário registrado com sucesso!');
    
            navigate('/');
        } catch (err) {
    
            console.error('Erro durante o registro: ', err);
            alert('Erro ao registrar!');
        }
    }

    return (
        <div className="vaporwave-container">
            <div className='vaporwave-header'>
                <img src="/logo-fixable.png" alt="Fixable" className="logo"/>
                <h1>Fixable</h1>
                <p>Entre na rede. Personalize. Conecte</p>
            </div>
    
            <div className="login-box">
                <form onSubmit={handleRegister}>
                <input type="text" placeholder="Novo usuário" value={username} onChange={(e) => setUsername(e.target.value)}/>
                <br/>
    
                <input type="password" placeholder="Crie uma senha" value={password} onChange={(e) => setPassword(e.target.value)}/>
                <br/>
    
                <button type="submit">Cadastrar</button>
                </form>
                <button onClick={() => navigate('/')}>Voltar ao Login</button>
            </div>
    
        </div>
    );
}

export default Register;
