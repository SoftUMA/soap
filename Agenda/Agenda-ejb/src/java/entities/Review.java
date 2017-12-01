/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author neko250
 */
@Entity
@Table(name = "Review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
    , @NamedQuery(name = "Review.findByEvent", query = "SELECT r FROM Review r WHERE r.reviewPK.event = :event")
    , @NamedQuery(name = "Review.findByAuthor", query = "SELECT r FROM Review r WHERE r.reviewPK.author = :author")
    , @NamedQuery(name = "Review.findByComment", query = "SELECT r FROM Review r WHERE r.comment = :comment")
    , @NamedQuery(name = "Review.findByScore", query = "SELECT r FROM Review r WHERE r.score = :score")})
public class Review implements Serializable {

    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 2000000000)
    private String comment;
    @Basic(optional = false)
    @NotNull()
    private int score;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReviewPK reviewPK;
    @JoinColumn(name = "author", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne
    private User user;
    @JoinColumn(name = "event", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Event event1;

    public Review() {
    }

    public Review(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }

    public Review(ReviewPK reviewPK, String comment, int score) {
        this.reviewPK = reviewPK;
        this.comment = comment;
        this.score = score;
    }

    public Review(Integer event, String author) {
        this.reviewPK = new ReviewPK(event, author);
    }

    public ReviewPK getReviewPK() {
        return reviewPK;
    }

    public void setReviewPK(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent1() {
        return event1;
    }

    public void setEvent1(Event event1) {
        this.event1 = event1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewPK != null ? reviewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewPK == null && other.reviewPK != null) || (this.reviewPK != null && !this.reviewPK.equals(other.reviewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Review[ reviewPK=" + reviewPK + " ]";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
