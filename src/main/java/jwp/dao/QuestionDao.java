package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {

    private final EntityManager em;

    @Transactional
    public Question insert(Question question){
        em.persist(question);
        return question;
    }

    @Transactional
    public void update(Question question) throws SQLException {
        em.merge(question);
    }

    @Transactional
    public void delete(int questionId) throws SQLException {
        Question question = em.find(Question.class, questionId);
        if(question != null){
            em.remove(question);
        }
    }

    public List<Question> findAll() throws SQLException {
        return em.createQuery("SELECT q FROM Question q ORDER BY q.questionId", Question.class).getResultList();
    }

    public Question findByQuestionId(int questionId) throws SQLException {
        return em.find(Question.class, questionId);
    }
}
