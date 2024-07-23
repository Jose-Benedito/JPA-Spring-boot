package com.bookstore.jpa.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tb_book")
public class BookModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    // não nulos , title não repetidos
    @Column(nullable = false, unique = true)
    private String title;

    // Relacionamento -> publisher
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne //(fetch = FetchType.LAZY) -> para carregar sem o relacionamento
    @JoinColumn(name="publisher_id")
    private PublisherModel publisher;

    //Relacionamento -> author
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany //(fetch = FetchType.LAZY) -> para carregar sem o relacionamento
    @JoinTable( //tabela auxiliar para unir as duas entidades
            name = "tb_book_author",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private Set<AuthorModel> authors = new HashSet<>();

    // Relacionamento -> review
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL) // operações serão feitas em cascata para o review
    private ReviewModel review;

    // getter and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public PublisherModel getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherModel publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorModel> authors) {
        this.authors = authors;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }
}