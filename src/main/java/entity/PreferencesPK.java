/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;


/**
 *
 * @author neko250
 */

public class PreferencesPK implements Serializable {
 
    private String user;
   
    private Integer event;

    public PreferencesPK() {
    }

    public PreferencesPK(String user, Integer event) {
        this.user = user;
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user != null ? user.hashCode() : 0);
        hash += (event != null ? event.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferencesPK)) {
            return false;
        }
        PreferencesPK other = (PreferencesPK) object;
        if ((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user)))
            return false;
        if ((this.event == null && other.event != null) || (this.event != null && !this.event.equals(other.event)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "entity.PreferencesPK[ user=" + user + ", event=" + event + " ]";
    }

}
