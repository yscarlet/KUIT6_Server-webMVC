package jwp.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.Question;
import jwp.model.User;

import java.sql.SQLException;
import java.util.List;

/*
CREATE TABLE QUESTIONS (
                           questionId 			bigint				auto_increment,
                           writer				varchar(30)			NOT NULL,
                           title				varchar(50)			NOT NULL,
                           contents			varchar(5000)		NOT NULL,
                           createdDate			timestamp			NOT NULL,
                           countOfAnswer int,
                           PRIMARY KEY               (questionId)
);
 */
public class QuestionDao {
    public final JdbcTemplate<Question> jdbcTemplate = new JdbcTemplate();
    // TODO read
    public List<Question> findAll() throws SQLException {
        String sql = "SELECT * FROM QUESTIONS";
        RowMapper rowMapper = rs-> new Question(rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    // TODO create
    public Question insert(Question question) throws SQLException {
        KeyHolder keyHolder = new KeyHolder();
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate) VALUES (?, ?, ?, ?)";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setObject(4, question.getCreatedDate());
        };
        jdbcTemplate.update(sql, pstmtSetter, keyHolder);
        return findByQuestionId((long)keyHolder.getId());
    }

    public Question findByQuestionId(Long questionId) throws SQLException {
        String sql = "SELECT * FROM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setLong(1, questionId);
        };
        RowMapper rowMapper = rs -> new Question(rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer")
                );
        return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
    }
}
