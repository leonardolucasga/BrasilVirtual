
function realizarLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    // Fazer a requisição à API
    fetch('http://localhost:8080/jogadores')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao obter dados dos jogadores.');
            }
            return response.json();
        })
        .then(data => {
            // Validar o email e senha 
            const jogador = data.content.find(jogador => jogador.email === email && jogador.senha === senha);

            if (jogador) {
                alert('Login bem-sucedido!');
                redirecionarParaPaginaCompra();
            } else {
                alert('Credenciais inválidas. Tente novamente.');
            }
        })
        .catch(error => {
            console.error('Erro na requisição:', error.message);
            alert('Erro ao realizar login. Tente novamente mais tarde.');
        });
}

function redirecionarParaPaginaCompra() {
    // SUBSTITUIR PAGE
    window.location.href = '/index.html';
}