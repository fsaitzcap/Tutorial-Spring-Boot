package com.ccsw.tutorial.game;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Game get(Long id) {
        return this.gameRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> findAll() {
        return (List<Game>) this.gameRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory, Long idAuthor) {

        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("category.id", ":", idCategory));
        GameSpecification authorSpec = new GameSpecification(new SearchCriteria("author.id", ":", idAuthor));
        Specification<Game> spec = Specification.where(titleSpec)
                .and(categorySpec)
                .and(authorSpec);

        return this.gameRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) {

        Game game;

        if (id == null) {
            game = new Game();
        } else {
            game = this.get(id);
        }

        BeanUtils.copyProperties(dto, game, "id", "author", "category");

        game.setAuthor(authorService.get(dto.getAuthor().getId()));
        game.setCategory(categoryService.get(dto.getCategory().getId()));

        this.gameRepository.save(game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

    }


}