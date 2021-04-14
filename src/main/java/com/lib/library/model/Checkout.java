package com.lib.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "checkouts")
public class Checkout {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date checkoutDate;
    @Column
    private Date dueDate;

    // book ids belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // one book has one checkout
    @OneToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    public Checkout() {
    }

    public Checkout(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "id=" + id +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
