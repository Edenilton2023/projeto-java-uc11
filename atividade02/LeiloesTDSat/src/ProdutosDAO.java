import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException; // Adicionado o import necessário
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    // Método para Cadastrar (Tarefa B)
    public void cadastrarProduto (ProdutosDTO produto){
       conn = new conectaDAO().connectDB();
       String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    // Método para Listar (Tarefa D)
    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();
        
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar: " + erro.getMessage());
        }
        return listagem;
    }
    public void venderProduto(int id) {
    // 1. Defina a query SQL para atualizar o status
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
    
    try {
        // 2. Prepare a conexão e a instrução
        conn = new conectaDAO().connectDB(); // Substitua pelo seu método de conexão
        prep = conn.prepareStatement(sql);
        
        // 3. Substitua o ponto de interrogação pelo ID recebido
        prep.setInt(1, id);
        
        // 4. Execute a atualização
        prep.executeUpdate();
        
        System.out.println("Produto atualizado com sucesso para 'Vendido'.");
        
    } catch (Exception e) {
        System.out.println("Erro ao vender produto: " + e.getMessage());
    }
}
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'"; 
    ArrayList<ProdutosDTO> Listagem = new ArrayList<>();
    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();
        
        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            Listagem.add(produto);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage());
    }
    return Listagem;
}
}