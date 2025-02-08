package edu.sc.lms.repository;

import edu.sc.lms.repository.custom.impl.*;
import edu.sc.lms.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType daoType){
        switch (daoType){
            case CIRCULATION: return (T) new CirculationDaoImpl();
            case LOGIN: return (T) new LoginDaoImpl();
            case BOOKMANAGE: return (T) new BookManageDaoImpl();
            case DASHBOARD:return (T) new DashboardDaoImpl();
            case MEMBER:return (T) new MemberManageDaoImpl();
        }
        return null;
    }
}
