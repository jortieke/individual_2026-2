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
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
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
        if (!validado(digimon)){
            return ResponseEntity.status(400).build();
        }

        Integer countId = getCountId(digimon.getNome());

        if (countId != null && countId > 0) {
            return ResponseEntity.status(409).build();
        }

        String sql = """
                INSERT INTO digimon (nome, poder, atributo, nivel, familia, imagem, dataRegistro) 
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, digimon.getNome());
            ps.setInt(2, digimon.getPoder());
            ps.setString(3, digimon.getAtributo());
            ps.setString(4, digimon.getNivel().name()); // Salva o nome da constante do Enum
            ps.setString(5, digimon.getFamilia());
            ps.setString(6, digimon.getImagem());
            ps.setObject(7, digimon.getDataRegistro()); // Salva a data corretamente
            return ps;
        }, keyHolder);

        Integer idGerado = keyHolder.getKeyAs(Integer.class);
        digimon.setId(idGerado);

        return ResponseEntity.status(201).body(digimon);
    }

    private @Nullable Integer getCountId(String nome) {
        String sqlRepetido = """
                SELECT COUNT(*) FROM digimon
                    WHERE LOWER(nome) = LOWER(?);
                """;
        return jdbcTemplate.queryForObject(sqlRepetido, Integer.class, nome);
    }

    public Boolean validado(Digimon digimon){
        if (digimon.getNome() == null || digimon.getNome().isBlank() ||
                digimon.getPoder() == null || digimon.getPoder() < 0 ||
                digimon.getAtributo() == null || digimon.getAtributo().isBlank() ||
                digimon.getNivel() == null || // Valida se o Enum foi passado
                digimon.getFamilia() == null || digimon.getFamilia().isBlank() ||
                digimon.getImagem() == null || digimon.getImagem().isBlank() ||
                digimon.getDataRegistro() == null) {
            return false;
        }
        return true;
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
               UPDATE digimon 
               SET nome = ?, poder = ?, atributo = ?, nivel = ?, familia = ?, imagem = ?, dataRegistro = ? 
               WHERE id = ?;
               """;

        digimon.setId(id);

        jdbcTemplate.update(
                sql,
                digimon.getNome(),
                digimon.getPoder(),
                digimon.getAtributo(),
                digimon.getNivel().name(),
                digimon.getFamilia(),
                digimon.getImagem(),
                digimon.getDataRegistro(),
                id
        );

        return ResponseEntity.status(200).body(digimon);
    }
}