package ohtu;

import ohtu.io.StubIO;
import ohtu.io.IO;
import ohtu.services.AuthenticationService;
import ohtu.data_access.InMemoryUserDao;
import ohtu.data_access.UserDao;
import ohtu.io.ConsoleIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

    private IO io;
    private AuthenticationService auth;

    @Autowired
    public App(IO io, AuthenticationService auth) {
        this.io = io;
        this.auth = auth;
    }

    public String[] ask() {
        String[] userPwd = new String[2];
        userPwd[0] = io.readLine("username:");
        userPwd[1] = io.readLine("password:");
        return userPwd;
    }

    public void run() {
        while (true) {
            String command = io.readLine(">");

            if (command.isEmpty()) {
                break;
            }

            if (command.equals("new")) {
                String[] usernameAndPasword = ask();
                if (auth.createUser(usernameAndPasword[0], usernameAndPasword[1])) {
                    io.print("new user registered");
                } else {
                    io.print("new user not registered");
                }

            } else if (command.equals("login")) {
                String[] usernameAndPasword = ask();
                if (auth.logIn(usernameAndPasword[0], usernameAndPasword[1])) {
                    io.print("logged in");
                } else {
                    io.print("wrong username or password");
                }
            }

        }
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");
 
        App application = ctx.getBean(App.class);
        application.run();
    }
    
    // testej� debugatessa saattaa olla hy�dyllist� testata ohjelman ajamista
    // samoin kuin testi tekee, eli injektoimalla k�ytt�j�n sy�tteen StubIO:n avulla
    //
    // UserDao dao = new InMemoryUserDao();  
    // StubIO io = new StubIO("new", "eero", "sala1nen" );   
    //  AuthenticationService auth = new AuthenticationService(dao);
    // new App(io, auth).run();
    // System.out.println(io.getPrints());
}