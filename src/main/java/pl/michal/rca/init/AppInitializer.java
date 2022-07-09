package pl.michal.rca.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.models.User;
import pl.michal.rca.repositories.AdminVariableRepository;
import pl.michal.rca.repositories.ReceiptRepository;
import pl.michal.rca.repositories.ReimbursementRepository;
import pl.michal.rca.repositories.UserRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class AppInitializer implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(AppInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce){
        logger.info("Creating tables and data ...");
        UserRepository userRepository = new UserRepository();
        AdminVariableRepository adminVariableRepository = new AdminVariableRepository();
        ReceiptRepository receiptRepository = new ReceiptRepository();
        ReimbursementRepository reimbursementRepository =new ReimbursementRepository();
        userRepository.createDatabaseIfNotExists();
        logger.info("Database Reimbursement created");
        userRepository.createUserTableIfNotExists();
        logger.info("User table created");
        adminVariableRepository.createAdminVariablesTableIfNotExists();
        logger.info("Admin variable table created");
        reimbursementRepository.createReimbursementsTableIfNotExists();
        logger.info("Reimbursements table created");
        receiptRepository.createReceiptsTableIfNotExists();
        logger.info("Receipts table created");

        List<User> users = List.of(new User("test1", "test1@test.pl", "test1", "USER"),
                new User("test2", "test2@test.pl", "test2", "USER"),
                new User("admin", "admin@test.pl", "admin", "ADMIN"));

        for(User user:users){
            User createdUser = userRepository.create(user);
            logger.info("Created user: {}", createdUser);
        }
        List<AdminVariable> variables = List.of(
                new AdminVariable("rate", "allowance",15.00,"active"),
                new AdminVariable("rate", "for km",0.3,"active"),
                new AdminVariable("limit", "limit total",2000.00,"active"),
                new AdminVariable("limit", "limit km",100.00,"active"),
                new AdminVariable("receiptType", "taxi",120.00,"active"),
                new AdminVariable("receiptType", "hotel",320.00,"active"),
                new AdminVariable("receiptType", "air ticket",420.00,"active"),
                new AdminVariable("receiptType", "train",220.00,"active"),
                new AdminVariable("receiptType", "dinner",202.00,"active"),
                new AdminVariable("receiptType", "recreation",100.00,"active")
        );
        for(AdminVariable adminVariable:variables){
            AdminVariable createdVariable = adminVariableRepository.create(adminVariable);
            logger.info("Created variable: {}", createdVariable);
        }


    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Removing tables ...");
        UserRepository userRepository = new UserRepository();
        AdminVariableRepository adminVariableRepository = new AdminVariableRepository();
        ReceiptRepository receiptRepository = new ReceiptRepository();
        ReimbursementRepository reimbursementRepository =new ReimbursementRepository();
        receiptRepository.dropReceiptTableIfExits();
        logger.info("Receipts table removed");
        adminVariableRepository.dropAdminVariablesTableIfExits();
        logger.info("Admin variable table removed");
        reimbursementRepository.dropReimbursementsTableIfExists();
        logger.info("Reimbursements table removed");
        userRepository.dropUserTableIfExists();
        logger.info("User table removed");


    }
}
