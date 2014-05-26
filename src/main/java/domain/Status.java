package domain;

import entities.annotations.EntityDescriptor;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@EntityDescriptor(hidden = true)
public abstract class Status implements Serializable {

    @Id
    private Long id;

    @Column(length = 40, nullable = false)
    private String description;

    @Transient
    protected Overtime overtime;

    public abstract void approve();

    public abstract void reject();

    public abstract void pay();

    public abstract void revert(String remark);

    // <editor-fold defaultstate="collapsed" desc="Get´s e Set´s">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descricao) {
        this.description = descricao;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public void setOvertime(Overtime ocorrencia) {
        this.overtime = ocorrencia;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="equals e hashcode">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Status other = (Status) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 11 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.id == null ? "<Novo>" : description;
    }// </editor-fold>
}
