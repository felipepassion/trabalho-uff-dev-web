package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Usuario;

public class UsuarioDAO {

    public void Inserir(Usuario usuario) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO administrador (nome, cpf, senha)"
                    + " VALUES (?,?,?)");
            sql.setString(1, usuario.getNome());
            sql.setString(2, usuario.getCpf());
            sql.setString(3, usuario.getSenha());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public Usuario getUsuario(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            Usuario usuario = new Usuario();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    usuario.setId(Integer.parseInt(resultado.getString("ID")));
                    usuario.setNome(resultado.getString("NOME"));
                    usuario.setCpf(resultado.getString("CPF"));
                    usuario.setSenha(resultado.getString("SENHA"));
                }
            }
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (get) incorreta - " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Usuario Usuario) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE administrador SET nome = ?, cpf = ?, senha = ?  WHERE ID = ? ");
            sql.setString(1, Usuario.getNome());
            sql.setString(2, Usuario.getCpf());
            sql.setString(3, Usuario.getSenha());
            sql.setInt(4, Usuario.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta - " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Usuario Usuario) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM administrador WHERE ID = ? ");
            sql.setInt(1, Usuario.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta - " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Usuario> ListaDeUsuarios() {
        ArrayList<Usuario> meusUsuarios = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM administrador order by nome";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Usuario usuario = new Usuario(resultado.getString("NOME"),
                            resultado.getString("CPF"),
                            resultado.getString("SENHA"));
                    usuario.setId(Integer.parseInt(resultado.getString("id")));
                    meusUsuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeUsuarios) incorreta - " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return meusUsuarios;
    }

    public Usuario Logar(Usuario usuario) throws SQLException, Exception  {
        Conexao conexao = new Conexao();
       
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM administrador WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, usuario.getCpf());
            sql.setString(2, usuario.getSenha());
            ResultSet resultado = sql.executeQuery();
            Usuario usuarioObtido = new Usuario();
            if (resultado != null) {
                while (resultado.next()) {
                    usuarioObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    usuarioObtido.setNome(resultado.getString("NOME"));
                    usuarioObtido.setCpf(resultado.getString("CPF"));
                    usuarioObtido.setSenha(resultado.getString("SENHA"));
                }
            }
            return usuarioObtido;

        
    }

}