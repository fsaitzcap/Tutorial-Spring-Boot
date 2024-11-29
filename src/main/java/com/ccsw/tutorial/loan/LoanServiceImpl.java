package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.exception.ConflictException;
import com.ccsw.tutorial.common.exception.NotFoundException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author ccsw
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    //--
    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", dto.getClientId()));

        LoanSpecification startLtOrEqSpec = new LoanSpecification(new SearchCriteria("dateIni", "<=", dto.getDateFinal()));
        LoanSpecification endGtOrEqSpec = new LoanSpecification(new SearchCriteria("dateFinal", ">=", dto.getDateIni()));

        Specification<Loan> spec = Specification.where(clientSpec).and(gameSpec).and(startLtOrEqSpec).and(endGtOrEqSpec);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws ConflictException {

        checkGameByName(dto);
        clientWithTwoGames(dto);
        returnDateGreaterThanLoanDate(dto);

        Loan loan = new Loan();

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    private void checkGameByName(LoanDto dto) throws ConflictException {
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGame().getId()));

        LoanSpecification startLtOrEqSpec = new LoanSpecification(new SearchCriteria("dateIni", "<=", dto.getDateFinal()));
        LoanSpecification endGtOrEqSpec = new LoanSpecification(new SearchCriteria("dateFinal", ">=", dto.getDateIni()));

        Specification<Loan> spec = Specification.where(gameSpec).and(startLtOrEqSpec).and(endGtOrEqSpec);

        List<Loan> loans = this.loanRepository.findAll(spec);

        if (!loans.isEmpty()) {
            throw new ConflictException("Error: Juego ya en alquiler, elija otro juego");
        }
    }

    /**
     * {@inheritDoc}
     */
    private void clientWithTwoGames(LoanDto dto) throws ConflictException {
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", dto.getClient().getId()));

        LoanSpecification startLtOrEqSpec = new LoanSpecification(new SearchCriteria("dateIni", "<=", dto.getDateFinal()));
        LoanSpecification endGtOrEqSpec = new LoanSpecification(new SearchCriteria("dateFinal", ">=", dto.getDateIni()));

        Specification<Loan> spec = Specification.where(clientSpec).and(startLtOrEqSpec).and(endGtOrEqSpec);

        List<Loan> loans = this.loanRepository.findAll(spec);

        if (loans.size() > 1) {
            throw new ConflictException("Error: Cliente con dos o mas juegos alquilados, escoja otras fechas");
        }
    }

    /**
     * {@inheritDoc}
     */
    private void returnDateGreaterThanLoanDate(LoanDto dto) throws ConflictException {

        LocalDate dateLoan = dto.getDateIni();
        LocalDate dateReturn = dto.getDateFinal();

        if ((ChronoUnit.DAYS.between(dateLoan, dateReturn) > 14)) {
            throw new ConflictException("Error: No puede alquilar un juego más de 14 días, por favor elija otras fechas");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws NotFoundException {
        if (this.get(id) == null) {
            throw new NotFoundException("Error: No existe id de préstamo");
        }
        this.loanRepository.deleteById(id);
    }

}


