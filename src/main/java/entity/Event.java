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

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
/**
 *
 * @author neko250
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
   @Id
    private Long  id;
 
    private String name;
  
    private String description;
  
    private String image;

    private String startDate;

    private String endDate;
   
    private String address;

    private String price;
   
    private String shopUrl;

    private String approved;

    
    @Load private Ref<Category> category;
    
    @Index
    @Load private Ref<User> author;    
    @Index
    @Load private Ref<Collection<Ref<Review>>> reviewCollection;    
    @Index
    @Load private Ref<Collection<Ref<Preferences>>> preferencesCollection;

    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, String name, String description, String image, String startDate, String endDate, String address, String price, String shopUrl, String approved) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return category.get();
    }

    public void setCategory(Category category) {
        this.category = Ref.create(category);
    }

    public User getAuthor() {
        return author.get();
    }

    public void setAuthor(User author) {
        this.author =Ref.create(author);
    }

    @XmlTransient
    public Collection<Ref<Review>> getReviewCollection() {
        return reviewCollection.get();
    }

    public void setReviewCollection(Collection<Review> reviewCollection) {
        this.reviewCollection.create(reviewCollection);
    }

    @XmlTransient
    public Collection<Ref<Preferences>> getPreferencesCollection() {
        return preferencesCollection.get();
    }

    public void setPreferencesCollection(Collection<Preferences> preferencesCollection) {
        this.preferencesCollection.create(preferencesCollection);
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
        return "entity.Event[ id=" + id + " ]";
    }

}
