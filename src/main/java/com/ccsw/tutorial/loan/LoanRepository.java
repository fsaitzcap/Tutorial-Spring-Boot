package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author ccsw
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {


    @Override
    @EntityGraph(attributePaths = {"game", "client", "game.author", "game.category"})
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"game", "client", "game.author", "game.category"})
    List<Loan> findAll(Specification<Loan> spec);

}
