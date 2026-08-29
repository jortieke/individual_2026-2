package sptech.school.digimon;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/digimons")
public class DigimonController {
    private final JdbcTemplate jdbcTemplate;

    public DigimonController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Digimon>> listarTodos() {
        String sql = """
                SELECT * FROM digimon;
                """;

        List<Digimon> digimons = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Digimon.class));

        return ResponseEntity.status(200).body(digimons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Digimon> listarPorId(@PathVariable Integer id) {
        String sql = """
                SELECT * FROM digimon WHERE id = ?;
                """;

        List<Digimon> digimons = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Digimon.class),
                id);

        if (digimons.isEmpty()) return ResponseEntity.status(404).build();

        return ResponseEntity.status(200).body(digimons.getFirst());
    }

    @PostMapping
    public ResponseEntity<Digimon> cadastrar(@RequestBody Digimon digimon) {
        String nome = digimon.getNome();
        String atributo = digimon.getAtributo();
        String nivel = digimon.getNome();
        String familia = digimon.getFamilia();
        String imagem = digimon.getImagem();

        if (!validado(digimon)){
            return ResponseEntity.status(400).build();
        } else {
            Integer countId = getCountId(nome);

            if(countId != null && countId > 0) {
                return ResponseEntity.status(409).build();
            } else {
                String sql = """
                INSERT INTO digimon (nome, atributo, nivel, familia, imagem) VALUES (?, ?, ?, ?, ?);
                """;

                KeyHolder keyHolder = new GeneratedKeyHolder();

                jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, nome);
                    ps.setString(2, atributo);
                    ps.setString(3, nivel);
                    ps.setString(4, familia);
                    ps.setString(5, imagem);
                    return ps;
                }, keyHolder);

                Integer idGerado = keyHolder.getKeyAs(Integer.class);
                digimon.setId(idGerado);

                return ResponseEntity.status(201).body(digimon);
            }
        }
    }

    private @Nullable Integer getCountId(String nome) {
        String sqlRepetido = """
                SELECT COUNT(*) FROM digimon
                    WHERE LOWER(nome) = LOWER(?);
                """;

        return jdbcTemplate.queryForObject(sqlRepetido, Integer.class, nome);
    }

    public Boolean validado(Digimon digimon){
        String nome = digimon.getNome();
        String atributo = digimon.getAtributo();
        String nivel = digimon.getNome();
        String familia = digimon.getFamilia();
        String imagem = digimon.getImagem();

        if (nome == null || nome.isBlank() ||
                atributo == null || atributo.isBlank() ||
                nivel == null || nivel.isBlank() ||
                familia == null || familia.isBlank() ||
                imagem == null || imagem.isBlank()) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean existe(Integer id){
        String sql = """
                SELECT COUNT(*) FROM digimon WHERE id = ?;
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count != 0;
    }

    public Boolean existeOutroComMesmoNome(String nome, Integer idAtual) {
        String sql = """
            SELECT COUNT(*) FROM digimon
            WHERE LOWER(nome) = LOWER(?) 
            AND id <> ?;
            """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nome, idAtual);
        return count != null && count > 0;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        String sql = """
                DELETE FROM digimon WHERE id = ?;
                """;

        if (!existe(id))
            return ResponseEntity.status(404).build();

        jdbcTemplate.update(sql, id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Digimon> atualizar(@PathVariable Integer id, @RequestBody Digimon digimon){
        if(!validado(digimon))
            return ResponseEntity.status(400).build();

        if (!existe(id))
            return ResponseEntity.status(404).build();

        if (existeOutroComMesmoNome(digimon.getNome(), id))
            return ResponseEntity.status(409).build();

        String sql = """
               UPDATE digimon SET nome = ?, atributo = ?, nivel = ?, familia = ?, imagem = ? WHERE id = ?;
               """;

        String nome = digimon.getNome();
        String atributo = digimon.getAtributo();
        String nivel = digimon.getNome();
        String familia = digimon.getFamilia();
        String imagem = digimon.getImagem();

        digimon.setId(id);

        jdbcTemplate.update(sql, nome, atributo, nivel, familia, imagem, id);
        return ResponseEntity.status(200).body(digimon);
    }
}