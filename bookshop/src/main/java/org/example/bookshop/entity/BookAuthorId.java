package org.example.bookshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorId implements Serializable {
    @Column(name = "BOOK_ID")
    private Long bookID;

    @Column(name = "AUTHOR_ID")
    private Long authorID;

    public BookAuthorId() {}

    public BookAuthorId(Long bookID, Long authorID) {
        this.bookID = bookID;
        this.authorID = authorID;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public Long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Long authorID) {
        this.authorID = authorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorId that = (BookAuthorId) o;
        return Objects.equals(bookID, that.bookID) &&
                Objects.equals(authorID, that.authorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID, authorID);
    }

    @Override
    public String toString() {
        return "BookAuthorId{" +
                "bookID=" + bookID +
                ", authorID=" + authorID +
                '}';
    }
}