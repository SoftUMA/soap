/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 *
 * @author Lore
 */

@Entity
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long  id;

    private String comment;
    private String score;
    
    @Index
    @Load private Ref<User> author;  
    @Index
    @Load private Ref<Event> event;
    
    
    
    public Review() {
    }

    public Review(Long id, String comment, String score) {
        this.id = id;
        this.comment = comment;
        this.score = score;
       
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public User getAuthor() {
        return author.get();
    }

    public void setAuthor(User author) {
        this.author = Ref.create(author);
    }

    public Event getEvent() {
		return event.get();
    }

    public void setEvent(Event event) {
        this.event = Ref.create(event);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "entity.Review[ id=" + id + " ]";
    }

}
