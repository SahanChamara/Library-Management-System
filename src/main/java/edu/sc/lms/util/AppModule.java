package edu.sc.lms.util;

import com.google.inject.AbstractModule;
import edu.sc.lms.repository.custom.BookManageDao;
import edu.sc.lms.repository.custom.CirculationDao;
import edu.sc.lms.repository.custom.DashboardDao;
import edu.sc.lms.repository.custom.LoginDao;
import edu.sc.lms.repository.custom.impl.*;
import edu.sc.lms.service.custom.*;
import edu.sc.lms.service.custom.impl.*;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BookManageService.class).to(BookManageServiceImpl.class);
        bind(CirculationService.class).to(CirculationServiceImpl.class);
        bind(DashBoardService.class).to(DashboardServiceImpl.class);
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(MemberService.class).to(MemberManagerServiceImpl.class);

        bind(BookManageDao.class).to(BookManageDaoImpl.class);
        bind(DashboardDao.class).to(DashboardDaoImpl.class);
        bind(LoginDao.class).to(LoginDaoImpl.class);

        /*bind(CirculationDao.class).to(CirculationDaoImpl.class);
        bind(LoginDao.class).to(LoginDaoImpl.class);
        bind(MemberManageDaoImpl.class).to(MemberManageDaoImpl.class);*/

    }
}
