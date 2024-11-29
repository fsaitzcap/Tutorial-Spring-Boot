package com.ccsw.tutorial.game.model;

import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.category.model.CategoryDto;

public class GameDto {

    private Long id;

    private String title;

    private String age;

    public Long getId() {
        return id;
    }

    private CategoryDto categoryDto;

    private AuthorDto authorDto;

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public CategoryDto getCategory() {
        return categoryDto;
    }

    public void setCategory(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public AuthorDto getAuthor() {
        return authorDto;
    }

    public void setAuthor(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

}
