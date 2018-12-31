package my.edu.tarc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        //create instance of a database
        AppDatabase db = AppDatabase.getDatabase(application);

        userDao = db.userDao();
        //load all records to the livedata
        allUsers = userDao.loadAllUsers();
    }
    
    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
    
    public void insertUser(User user){
        new insertAsyncTask(userDao).execute(user);
    }

    public void deleterUser(User user){
        new deleteAsynTask(userDao).execute(user);
    }
    //<Param, Progress, Results>
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public insertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }


        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
    private class deleteAsynTask extends AsyncTask<User,Void,Void> {
        public UserDao userDao;

        public deleteAsynTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }


}
