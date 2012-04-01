/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ohtu.domain.User;

/**
 *
 * @author Annurgaia
 */
public class FileUserDao implements UserDao {
    
    private File users;

    public FileUserDao(File users) {
        if(users.exists())
            this.users = users;
        else {
            this.users = new File("users.txt");
        }
    }

    @Override
    public List<User> listAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User findByName(String name) {
        User user = null;
        String[] userInfo;  
        String userToFind = null;
        Scanner reader = null;
        try {
            reader = new Scanner(users);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(reader.hasNextLine()) {
            userToFind = reader.nextLine();
            if(userToFind.contains(name)) {
                userInfo = userToFind.split(":");
                break;
            }
        }
        
        user = new User(userInfo[0], userInfo[1]);
        return user;
    }

    @Override
    public void add(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}