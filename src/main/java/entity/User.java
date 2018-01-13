/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author neko250
 */
@com.googlecode.objectify.annotation.Entity

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
 
    private String email;
    private String name;
    private String surname;
    private String role;
    private Collection<Event> eventCollection;
    private Collection<Review> reviewCollection;
    private Collection<Preferences> preferencesCollection;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String name, String surname, String role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlTransient
    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
    }

    @XmlTransient
    public Collection<Review> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Review> reviewCollection) {
        this.reviewCollection = reviewCollection;
    }

    @XmlTransient
    public Collection<Preferences> getPreferencesCollection() {
        return preferencesCollection;
    }

    public void setPreferencesCollection(Collection<Preferences> preferencesCollection) {
        this.preferencesCollection = preferencesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || email == null || !(object instanceof User))
            return false;

        User other = (User) object;
        return email.equals(other.getEmail());
    }

    @Override
    public String toString() {
        return "entity.User[ email=" + email + " ]";
    }

}
