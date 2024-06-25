package ru.itis.orisjavaproject.Repositories.Critria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomRepository  {

    private final EntityManager entityManager;

    public List<Subject> findMostFrequentSubjectsByUser(User user, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);
        Root<Subject> subject = cq.from(Subject.class);
        Join<Subject, Book> book = subject.join("books");
        Join<Book, User> bookUser = book.join("users");

        cq.where(cb.equal(bookUser, user));
        cq.groupBy(subject.get("id"));
        cq.orderBy(cb.desc(cb.count(subject)));

        return entityManager.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }


    public List<Author> findMostFrequentAuthorsByUser(User user, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<User> rootUser = cq.from(User.class);
        Join<User, Book> book = rootUser.join("books");
        Join<Book, Author> author = book.join("authors");

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Author> subqueryAuthor = subquery.from(Author.class);
        subquery.select(cb.count(subqueryAuthor));
        subquery.where(cb.equal(subqueryAuthor, author));

        cq.select(author);
        cq.where(cb.equal(rootUser, user));
        cq.groupBy(author);
        cq.orderBy(cb.desc(subquery));

        return entityManager.createQuery(cq)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}