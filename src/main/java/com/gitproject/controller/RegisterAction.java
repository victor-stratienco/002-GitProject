package com.gitproject.controller;

import com.gitproject.model.User;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Name("register")
public class RegisterAction implements Register
{
    @In
    private User user;

    @PersistenceContext
    private EntityManager em;

    @Logger
    private Log log;

    public String register()
    {
        List existing = em.createQuery("select username " +
                "from User " +
                "where username = #{user.username}")
            .getResultList();

        if (existing.size()==0)
        {
            em.persist(user);
            log.info("Registered new user #{user.username}");
            return "/registered.xhtml";
        }
        else
        {
            FacesMessages.instance().add("User #{user.username} already exists");
            return null;
        }
    }

}