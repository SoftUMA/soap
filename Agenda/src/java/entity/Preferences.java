/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author neko250
 */
@Entity
@Table(name = "Preferences")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preferences.findAll", query = "SELECT p FROM Preferences p")
    , @NamedQuery(name = "Preferences.findByUser", query = "SELECT p FROM Preferences p WHERE p.preferencesPK.user = :user")
    , @NamedQuery(name = "Preferences.findByEvent", query = "SELECT p FROM Preferences p WHERE p.preferencesPK.event = :event")
    , @NamedQuery(name = "Preferences.findByVisits", query = "SELECT p FROM Preferences p WHERE p.visits = :visits")})
public class Preferences implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferencesPK preferencesPK;
    @Column(name = "visits", nullable = false)
    private String visits;
    @JoinColumn(name = "event", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Event event1;
    @JoinColumn(name = "user", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne
    private User user1;

    public Preferences() {
    }

    public Preferences(PreferencesPK preferencesPK) {
        this.preferencesPK = preferencesPK;
    }

    public Preferences(PreferencesPK preferencesPK, String visits) {
        this.preferencesPK = preferencesPK;
        this.visits = visits;
    }

    public Preferences(String user, Integer event) {
        this.preferencesPK = new PreferencesPK(user, event);
    }

    public PreferencesPK getPreferencesPK() {
        return preferencesPK;
    }

    public void setPreferencesPK(PreferencesPK preferencesPK) {
        this.preferencesPK = preferencesPK;
    }

    public String getVisits() {
        return visits;
    }

    public void setVisits(String visits) {
        this.visits = visits;
    }

    public Event getEvent1() {
        return event1;
    }

    public void setEvent1(Event event1) {
        this.event1 = event1;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferencesPK != null ? preferencesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferences)) {
            return false;
        }
        Preferences other = (Preferences) object;
        if ((this.preferencesPK == null && other.preferencesPK != null) || (this.preferencesPK != null && !this.preferencesPK.equals(other.preferencesPK)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "entity.Preferences[ preferencesPK=" + preferencesPK + " ]";
    }

}
