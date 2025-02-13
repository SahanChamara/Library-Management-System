package edu.sc.lms.service;

import edu.sc.lms.service.custom.impl.*;
import edu.sc.lms.util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance ==null? instance =new ServiceFactory(): instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case LOGIN: return (T) new LoginServiceImpl();
            case MEMBER: return (T) new MemberManagerServiceImpl();
            case DASHBOARD: return (T) new DashboardServiceImpl();
            case BOOKMANAGE: return (T) new BookManageServiceImpl();
            case CIRCULATION: return (T) new CirculationServiceImpl();
            case REPORT:return (T) new ReportServiceImpl();
        }
        return null;
    }
}
