/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author neko250
 */
@Entity
@Table(name = "Event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
    , @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id")
    , @NamedQuery(name = "Event.findByName", query = "SELECT e FROM Event e WHERE e.name = :name")
    , @NamedQuery(name = "Event.findByDescription", query = "SELECT e FROM Event e WHERE e.description = :description")
    , @NamedQuery(name = "Event.findByImage", query = "SELECT e FROM Event e WHERE e.image = :image")
    , @NamedQuery(name = "Event.findByStartDate", query = "SELECT e FROM Event e WHERE e.startDate = :startDate")
    , @NamedQuery(name = "Event.findByEndDate", query = "SELECT e FROM Event e WHERE e.endDate = :endDate")
    , @NamedQuery(name = "Event.findByAddress", query = "SELECT e FROM Event e WHERE e.address = :address")
    , @NamedQuery(name = "Event.findByPrice", query = "SELECT e FROM Event e WHERE e.price = :price")
    , @NamedQuery(name = "Event.findByShopUrl", query = "SELECT e FROM Event e WHERE e.shopUrl = :shopUrl")
    , @NamedQuery(name = "Event.findByApproved", query = "SELECT e FROM Event e WHERE e.approved = :approved")})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "startDate", nullable = false)
    private String startDate;
    @Column(name = "endDate", nullable = false)
    private String endDate;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "shopUrl", nullable = false)
    private String shopUrl;
    @Column(name = "approved", nullable = false)
    private String approved;
    @JoinColumn(name = "category", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private Category category;
    @JoinColumn(name = "author", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private User author;
    @OneToMany(mappedBy = "event1")
    private Collection<Review> reviewCollection;
    @OneToMany(mappedBy = "event1")
    private Collection<Preferences> preferencesCollection;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, String name, String description, String image, String startDate, String endDate, String address, String price, String shopUrl, String approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.price = price;
        this.shopUrl = shopUrl;
        this.approved = approved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "entities.Event[ id=" + id + " ]";
    }

}
