package demo.domain;

import javax.persistence.*;
import java.util.Date;

public class AbstractDomainClass implements DomainObject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private Date dateCreated;
    private Date lastUpdated;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps(){
        lastUpdated = new Date();
        if (dateCreated == null){
            dateCreated = new Date();
        }
    }
}
