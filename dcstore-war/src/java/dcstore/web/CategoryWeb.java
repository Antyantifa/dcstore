// This file is a part of dcstore project,
// licensed under GPLv2
//
// Dominik Cebula
// dominikcebula@gmail.com
package dcstore.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import dcstore.jpa.CategoryEntity;
import dcstore.ejb.CategoryBeanLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;

/**
 *
 * @author dcebula
 */
@ManagedBean
@RequestScoped
public class CategoryWeb {

    @EJB
    private CategoryBeanLocal categoryBean;
    private Long id;
    @Pattern(regexp = "[A-Za-z0-9-_ ]+")
    private String name;
    @Pattern(regexp = "[A-Za-z0-9-_ ]*")
    private String nameNew;

    public String getNameNew() {
        return nameNew;
    }

    public void setNameNew(String nameNew) {
        this.nameNew = nameNew;
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

    public List<CategoryEntity> getAll() {
        List<CategoryEntity> ret = null;

        try {
            ret = categoryBean.getAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }

        return ret;
    }

    public void add() {
        try {
            categoryBean.add(nameNew);
            this.nameNew = "";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public void edit() {
        try {
            categoryBean.edit(id, name);
            FacesContext.getCurrentInstance().getExternalContext().dispatch("admin-categories.xhtml?id=-1");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }

    public void del(Long id) {
        try {
            categoryBean.del(id);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Error while processing request: " + e.getMessage()));
        }
    }
}