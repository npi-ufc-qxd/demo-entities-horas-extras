package domain;

import entities.Repository;
import entities.annotations.ActionDescriptor;
import entities.annotations.Editor;
import entities.annotations.ParameterDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import entities.descriptor.PropertyType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Views({
    //<editor-fold defaultstate="collapsed" desc="RequestOvertime">
    @View(name = "RequestOvertime",
      title = "Request Overtime",
      members = "[#employee;#beginning;#ending;#description;request()]",
      namedQuery = "Select new domain.Overtime()",
      roles = "LOGGED"),
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AuthorizeOvertime">
    @View(name = "AuthorizeOvertime",
      title = "Authorize Overtime",
      members = "[employee:2;beginning,ending;#description,remark;[approve(),reject()]]",
      namedQuery = "Select ot from Overtime ot,StatusWaitingForApproval st where ot.status = st",
      roles = "Admin,Supervisor"),
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AuthorizePaymentOfOvertime">
    @View(name = "AuthorizePaymentOfOvertime",
      title = "Authorize Payment of Overtime",
      members = "Request Overtime[[employee;beginning;ending],description],Action[pay();revert();reject()]",
      namedQuery = "Select ot from Overtime ot,StatusWaitingForPayment st where ot.status = st",
      roles = "Admin,RH"),
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Overtimes">
    @View(name = "Overtimes",
      title = "Overtimes",
      filters = "employee",
      members = "[employee;beginning;ending;remark;description;status;[approve(),reject(),pay(),revert()]]",
      template = "@FORM+@FILTER",
      roles = "Admin")
        //</editor-fold>
})
public class Overtime implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @ManyToOne
    private Employee employee;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginning;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date ending;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Lob
    @NotEmpty
    private String description;

    @Lob
    private String remark;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Status status = new StatusWaitingForApproval(this);

    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ActionDescriptor(refreshView = true)
    public String request() {
        Repository.save(this);
        return "i18n['domain.Overtime.request.sucess']";
    }

    @ActionDescriptor(refreshView = true)
    public void approve() {
        this.status.setOvertime(this);
        status.approve();
        Repository.save(this);
    }

    @ActionDescriptor(confirm = true, image = "image:trash", refreshView = true)
    public void reject() {
        this.status.setOvertime(this);
        status.reject();
        Repository.save(this);
    }

    @ActionDescriptor(image = "image:round_ok", refreshView = true)
    public void pay() {
        this.status.setOvertime(this);
        status.pay();
        Repository.save(this);
    }

    @ActionDescriptor(image = "image:trackback", refreshView = true)
    public void revert(
            @Editor(propertyType = PropertyType.MEMO)
            @ParameterDescriptor(displayName = "Remark") String remark) {
        this.status.setOvertime(this);
        status.revert(remark);
        Repository.save(this);
    }
}
