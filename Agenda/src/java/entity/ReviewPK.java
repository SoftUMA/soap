/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author neko250
 */
@Embeddable
public class ReviewPK implements Serializable {

    @Column(name = "event")
    private Integer event;
    @Column(name = "author")
    private String author;

    public ReviewPK() {
    }

    public ReviewPK(Integer event, String author) {
        this.event = event;
        this.author = author;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (event != null ? event.hashCode() : 0);
        hash += (author != null ? author.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReviewPK)) {
            return false;
        }
        ReviewPK other = (ReviewPK) object;
        if ((this.event == null && other.event != null) || (this.event != null && !this.event.equals(other.event)))
            return false;
        if ((this.author == null && other.author != null) || (this.author != null && !this.author.equals(other.author)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "entity.ReviewPK[ event=" + event + ", author=" + author + " ]";
    }

}
